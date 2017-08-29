# coding=utf-8
import os
import re
import sys
import urllib2

import requests
from bs4 import BeautifulSoup

# 基本配置
_host = 'http://linux.linuxidc.com/'
_file_pattern = 'linuxconf/download\.php\?file=.*'
_folder_pattern = 'index\.php\?folder=.*'


# 扫描页面
def scan_files(url, path, operator):
  try:
    response = urllib2.urlopen(url)
    html = response.read()
  except:
    sys.stderr.write('解析' + url + '出错,跳过当前页面')
    return

  soap = BeautifulSoup(html, 'html.parser')

  for fileURL in soap.find_all('a', href=re.compile(_file_pattern)):
    operator(fileURL, path)

  next_page = []
  next_path = []

  for folderDiv in soap.find_all('tr', class_='folder_bg'):
    folderURL = folderDiv.find('a', href=re.compile(_folder_pattern))
    # print folderURL
    next_page.append(_host + folderURL['href'])
    next_path.append(path + '/' + folderURL.string)

  for num in range(0, len(next_page) - 1):
    print '扫描到子目录:' + (next_page[num] + ' | ' + next_path[num]).encode('utf-8')
    scan_files(next_page[num], next_path[num], operator)


# 保存urls到文件
def download_urls(url, path):
  scan_files(url, path, save_urls)


# 回调处理
def save_urls(fileURL, path):
  record = fileURL.string + ' | ' + \
           _host + fileURL['href'] + ' | ' + path + '\n'
  print '扫描到文件:' + (fileURL.string).encode('utf-8')
  # print record
  record_file.write(record.encode('utf-8', 'ignore'))


# 下载文件
def download_files(url, path):
  scan_files(url, path, save_files)


# 回调处理
def save_files(fileURL, path):
  file_name = fileURL.string
  file_type = file_name[file_name.rfind('.'):]

  if not file_types.has_key(file_type):
    return

  print '扫描到文件:' + (file_name).encode('utf-8')
  r = requests.get(_host + fileURL['href'])
  file_path = path + file_name
  index = file_path.rfind('/')
  try:
    os.makedirs(file_path[0:index])
  except:
    sys.stderr.write('dir is existed :' + file_path[0:index] + '\n')
  with open(file_path, "wb") as file:
    file.write(r.content)


# main
if __name__ == '__main__':
  record_file = open('LinuxConf-Download.txt', 'w')
  download_urls(_host + 'index.php', '.')
  record_file.flush()
  record_file.close()

  # 指定下载的文件类型
  # file_types = {'.pdf': 1}
  # download_files(
  #     'http://linux.linuxidc.com/index.php?folder=MjAxN8Tq18rBzw==',
  #     'download')
