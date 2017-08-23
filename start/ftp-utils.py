# coding=utf-8
import os
import sys
from ftplib import FTP  # 静态导入ftplib.FTP


class FTPUtil(object):
  def __init__(self):
    self.ftp = None

  def login(self, host, username, password):
    self.ftp = FTP()
    self.ftp.set_debuglevel(0)  # 0关闭调试信息
    self.ftp.set_pasv(False)
    self.ftp.connect(host, port=21)
    self.ftp.login(username, password)
    print(self.ftp.getwelcome())
    print('当前路径:', self.ftp.pwd())

  def ftp_up(self, localDir='./', targetDir='./', filterDirs=[]):
    currentDir = self.cd(targetDir)
    if os.path.isdir(localDir):
      filterMap = {}
      for filter in filterDirs:
        filterMap[filter] = None
      self.upload_dir(localDir, currentDir, filterMap)
    elif os.path.isfile(localDir):
      self.upload_file(localDir, currentDir)

  def upload_dir(self, localDir='./', targetDir='./', filterDirs={}):
    print('开始上传目录:', localDir, ' 到ftp:', self.ftp.pwd(), ' 的 ', targetDir)
    self.ftp.cwd(targetDir)  # 移动到目标目录

    for file in os.listdir(localDir):  # 遍历本地文件
      src = os.path.join(localDir, file)
      if os.path.isfile(src):  # 当前路径是文件类型
        self.upload_file(src, file)  # 上传到目标目录
      elif os.path.isdir(src):  # 当前路径是目录
        print('发现子目录:', src)
        if filterDirs.has_key(localDir.replace('\\', '/')):
          print('忽略目录:', localDir)
          return
        try:
          self.ftp.mkd(file)  # 创建目录
        except:
          sys.stderr.write('the dir is exists %s/n' % file)
        self.upload_dir(src, file, filterDirs)  # 递归 ,上传目录

    self.ftp.cwd('..')  # 当前目录上传完毕 返回上一级

  def upload_file(self, localDir='./', targetDir='./'):
    print('开始上传文件:', localDir, ' 到:', targetDir)
    self.ftp.storbinary('STOR %s' % targetDir, open(localDir, 'rb'))

  def logout(self):
    self.ftp.quit()
    self.ftp = None

  def cd(self, targetDir='./'):
    print('需要上传文件到:', targetDir)
    index = targetDir.rfind('/')
    length = len(targetDir)

    if index + 1 == length:
      try:
        self.ftp.mkd(targetDir)
      except:
        sys.stderr.write('the dir is exists %s' % file)
      self.ftp.cwd(targetDir)
      print('创建目标目录并移动到:', self.ftp.pwd())
      return './'
    elif index == -1:
      return targetDir
    else:
      parentPath = targetDir[0:index]
      try:
        self.ftp.mkd(parentPath)
      except:
        sys.stderr.write('the dir is exists %s' % file)
      self.ftp.cwd(parentPath)
      print('创建目标目录并移动到:', self.ftp.pwd())
      return targetDir[index + 1:]


# python 脚本,自动上传文件到服务器
# 可过滤部分文件
if __name__ == '__main__':
  _jarName = 'wechat-api-sample-1.0-SNAPSHOT.jar'
  _webDir = 'wechat-webapp/'
  _filterDirs = ['wechat-webapp/css/libs',
                 'wechat-webapp/css/images',
                 'wechat-webapp/css/fonts',
                 'wechat-webapp/js/libs',
                 'wechat-webapp/demos',
                 'wechat-webapp/error']

  ftpUtils = FTPUtil()
  ftpUtils.login('host', 'name', 'passwd')
  ftpUtils.ftp_up('target/' + _jarName, _jarName)
  ftpUtils.ftp_up(_webDir, _webDir, _filterDirs)
  ftpUtils.logout()
