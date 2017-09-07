# coding=utf-8
import calendar
import datetime

import requests
from bson import json_util

SEARCH_API = 'https://s.search.bilibili.com/cate/search'

ORDER_CLICK = 'click'  # 点击
ORDER_STOW = 'stow'  # 收藏
ORDER_SCORES = 'scores'  # 评分
ORDER_DM = 'dm'  # 弹幕
ORDER_COIN = 'coin'  # 硬币

COPYRIGHT_ALL = '-1'  # 全部
COPYRIGHT_OWN = '1'  # 原创

CATE_ID_LIST = {15: "连载剧集",
                17: "单机游戏",
                19: "Mugen",
                20: "宅舞",
                21: "日常",
                22: "鬼畜调教",
                24: "MAD·AMV",
                25: "MMD·3D",
                26: "音MAD",
                27: "综合",
                28: "原创音乐",
                29: "三次元音乐",
                30: "VOCALOID·UTAU",
                31: "翻唱",
                32: "完结动画",
                33: "连载动画",
                34: "完结剧集",
                37: "纪录片",
                39: "演讲•公开课",
                47: "短片·手书·配音",
                51: "资讯",
                54: "OP/ED/OST",
                59: "演奏",
                65: "网络游戏",
                71: "综艺",
                75: "动物圈",
                76: "美食圈",
                82: "电影相关",
                83: "其他国家",
                85: "短片",
                86: "特摄",
                95: "数码",
                96: "星海",
                98: "机械",
                121: "GMV",
                122: "野生技术协会",
                124: "趣味科普人文",
                126: "人力VOCALOID",
                127: "教程演示",
                128: "电视剧相关",
                130: "音乐选集",
                131: "Korea相关",
                136: "音游",
                137: "明星",
                138: "搞笑",
                145: "欧美电影",
                146: "日本电影",
                147: "国产电影",
                152: "官方延伸",
                153: "国产动画",
                154: "三次元舞蹈",
                156: "舞蹈教程",
                157: "美妆",
                158: "服饰",
                159: "资讯",
                161: "手工",
                162: "绘画",
                163: "运动",
                164: "健身",
                165: "广告",
                168: "国产原创相关",
                169: "布袋戏",
                170: "资讯",
                171: "电子竞技",
                172: "手机游戏",
                173: "桌游棋牌",
                174: "其他"}


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
