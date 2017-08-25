# coding=utf-8
import calendar
import datetime
from bson import json_util

import requests

SEARCH_API = 'https://s.search.bilibili.com/cate/search'

ORDER_CLICK = 'click'  # 点击
ORDER_STOW = 'stow'  # 收藏
ORDER_SCORES = 'scores'  # 评分
ORDER_DM = 'dm'  # 弹幕
ORDER_COIN = 'coin'  # 硬币

COPYRIGHT_ALL = '-1'  # 全部
COPYRIGHT_OWN = '1'  # 原创


class SearchRequest:
  def __init__(self, cate_id='1',
      time_from=None, time_to=None,
      page=0, pagesize=100,
      order=ORDER_CLICK, copy_right=COPYRIGHT_ALL):
    if time_from is None:
      today = datetime.date.today()
      _, last_day = calendar.monthrange(today.year, today.month)
      time_from = today.strftime('%Y%m01')
      time_to = today.strftime('%Y%m' + str(last_day))

    self.parameters = {
      'main_ver': 'v3',
      'search_type': 'video',
      'view_type': 'hot_rank',
      'pic_size': '160x100',
      'order': order,
      'copy_right': copy_right,
      'cate_id': cate_id,
      'page': page,
      'pagesize': pagesize,
      'time_from': time_from,
      'time_to': time_to
    }
    self.hasNext = True

  def get(self):
    res = requests.get(SEARCH_API, params=self.parameters)
    print '发送请求:' + res.url.encode('utf-8')
    if not res.status_code == requests.codes.ok:
      Exception('Not expect status')
    return SearchResponse(res.content)

  def nextpage(self):
    self.parameters['page'] = self.parameters['page'] + 1

    res = requests.get(SEARCH_API, params=self.parameters)
    print '发送请求:' + res.url.encode('utf-8')
    if not res.status_code == requests.codes.ok:
      Exception('Not expect status')

    res = SearchResponse(res.content)
    if res.page == res.numPages:
      self.hasNext = False
    return res


class SearchResponse:
  def __init__(self, content):
    '''
    TODO
    解析json 变成固定的属性
    '''
    self._json = content
    self.json = json_util.loads(content)
    self.numPages = self.json['numPages']
    self.pagesize = self.json['pagesize']
    self.page = self.json['page']
    self.result = []
    for video in self.json['result']:
      self.result.append(Video(video))

  def printjson(self):
    tabs = ''
    s = ''
    flag = False
    for cc in self._json:
      if cc == '{' or cc == '[':
        tabs = tabs + '  '
        s = s + cc + '\n' + tabs
      elif not flag and cc == ',':
        s = s + cc + '\n' + tabs
      elif cc == '}' or cc == ']':
        tabs = tabs[0:len(tabs) - 2]
        s = s + '\n' + tabs + cc
      elif cc == '"' or cc == "'":
        flag = ~flag
        s = s + cc
      else:
        s = s + cc
    print s


class Video:
  def __init__(self, json):
    self._json = json
    self.badgepay = json['badgepay']
    self.play = json['play']
    self.description = json['description']
    self.pubdate = json['pubdate']
    self.title = json['title']
    self.review = json['review']
    self.pic = json['pic']
    self.mid = json['mid']
    self.id = json['id']
    self.arcurl = json['arcurl']
    self.tag = json['tag']
    self.video_review = json['video_review']
    self.author = json['author']
    self.favorites = json['favorites']
    self.duration = json['duration']
    self.type = json['type']
    self.arcrank = json['arcrank']
    self.senddate = json['senddate']

  def json(self):
    return self._json
