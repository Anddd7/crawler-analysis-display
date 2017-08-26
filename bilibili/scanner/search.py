# coding=utf-8
from bilibili.model.SearchModel import *
from bilibili.common import *
import pymongo

mongo_client = pymongo.MongoClient("localhost", 27017)
db = mongo_client['bilibili']


def scancateid():
  cate_list = []
  cate_num = []
  for cateid in range(200):
    try:
      req = SearchRequest(cateid, '20170501', '20170801', 1, 1)
      res = req.get()
      if res.numResults is not 0:
        cate_list.append(cateid)
        cate_num.append(res.numResults)
    except Exception,Argument:
      print Argument, ': ' + str(cateid) + '无效或无有效数据'

  print cate_list
  print cate_num


# 获取指定 类别 年月 的 视频信息
def insert(cate_id, year, month):
  title = 'search-' + yyyyMM(year, month)
  start, end = monthrange_yyyyMMdd(year, month)
  req = SearchRequest(cate_id, start, end)
  while req.hasNext:
    collection = db[title]
    for video in req.nextpage().result:
      print '插入视频信息: ' + str(video.id) + ' 到集合 ' + title
      collection.insert(video.json())
    print '当前页已加载 ,防扒等待...'
    time.sleep(0.5)


# scancateid()
for cateid in CATE_ID_LIST:
  for year in range(2017, 2018):
    for month in range(7, 8):
      print str(cateid) + '-' + str(year) + '-' + str(month)
      insert(cateid, year, month)
