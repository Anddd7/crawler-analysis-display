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

CATE_ID_LIST = [0, 1, 3, 4, 5, 11, 15, 17, 19, 20, 21, 22, 24, 25, 26, 27, 28,
                29, 30, 31, 32, 33, 34, 36, 37, 39, 47, 51, 54, 59, 65, 71, 75,
                76, 82, 83, 85, 86, 95, 96, 98, 119, 121, 122, 124, 126, 127,
                128, 129, 130, 131, 136, 137, 138, 145, 146, 147, 152, 153, 154,
                156, 157, 158, 159, 160, 161, 162, 163, 164, 165, 166, 167, 168,
                169, 170, 171, 172, 173, 174]


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

    self.cate_id = cate_id
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
      raise Exception('Not expect status')
    return SearchResponse(res.content, self.cate_id)

  def nextpage(self):
    self.parameters['page'] = self.parameters['page'] + 1

    res = requests.get(SEARCH_API, params=self.parameters)
    print '发送请求:' + res.url.encode('utf-8')
    if not res.status_code == requests.codes.ok:
      Exception('Not expect status')

    res = SearchResponse(res.content, self.cate_id)
    if res.numPages == 0 or res.page == res.numPages:
      self.hasNext = False
    return res


class SearchResponse:
  def __init__(self, content, cate_id):
    '''
    TODO
    解析json 变成固定的属性
    '''
    self._json = content
    self.json = json_util.loads(content)
    self.code = self.json['code']
    if self.code is not 0:
      raise Exception('not valid result')
    self.numPages = self.json['numPages']
    self.numResults = self.json['numResults']
    self.pagesize = self.json['pagesize']
    self.page = self.json['page']
    self.result = []
    for video in self.json['result']:
      self.result.append(Video(video, cate_id))

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
  def __init__(self, json, cate_id):
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

    self._json['cateid'] = cate_id
    self._cateid = cate_id

  def json(self):
    return self._json
