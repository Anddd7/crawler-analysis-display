# coding=utf-8
import calendar
import datetime
import json

import requests

SEARCH_API = 'https://s.search.bilibili.com/cate/search'

ORDER_CLICK = 'click'  # 点击
ORDER_STOW = 'stow'  # 收藏
ORDER_SCORES = 'scores'  # 评分
ORDER_DM = 'dm'  # 弹幕
ORDER_COIN = 'coin'  # 硬币

COPYRIGHT_ALL = '-1'
COPYRIGHT_OWN = '1'


class SearchRequest:
  def __init__(self, order=ORDER_CLICK, copy_right=COPYRIGHT_ALL, cate_id='22',
      page=1, pagesize=20,
      time_from=None, time_to=None):
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

  def get(self):
    res = requests.get(SEARCH_API, params=self.parameters)
    if not res.status_code == requests.codes.ok:
      Exception('Not expect status')
    return SearchResponse(res.content)


class SearchResponse:
  def __init__(self, content):
    '''
    TODO
    解析json 变成固定的属性
    '''
    self._json = content
    self.json = json.loads(content)
    # self.code = json['code']

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
