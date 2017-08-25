# coding=utf-8
from bilibili.model.SearchModel import *
from bilibili.common import *
import pymongo

mongo_client = pymongo.MongoClient("localhost", 27017)
db = mongo_client['bilibili']

# 获取指定月份的

# Mugen
cate_id = 19
year = 2017
month = 07

title = 'cate-' + str(cate_id) + '-' + yyyyMM(year, month)
start, end = monthrange_yyyyMMdd(year, month)
req = SearchRequest(cate_id, start, end)
while req.hasNext:
  collection = db[title]
  for video in req.nextpage().result:
    print '插入视频信息: ' + str(video.id) + ' 到集合 ' + title
    collection.insert(video.json())
