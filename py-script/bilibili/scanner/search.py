# coding=utf-8
import pymongo

from bilibili.common import *
from bilibili.model.SearchModel import *


# 获取指定 类别 年月 的 视频信息
def insert(cate_id, year, month):
  tablename = 'search_source_' + yyyyMM(year, month)
  start, end = monthrange_yyyyMMdd(year, month)
  req = SearchRequest(cate_id, start, end)
  while req.hasNext:
    for video in req.nextpage().result:
      print ('插入视频信息: ' + str(video.id) + ' 到集合 ' + tablename)
      cursor[tablename].insert(video.json())
    # print '当前页已加载 ,防扒等待...'
    time.sleep(0.1)


db = pymongo.MongoClient("localhost", 27017)
cursor = db['bilibili']


def run(sy, ey, sm, em):
  for cateid in CATE_ID_LIST:
    for year in range(sy, ey):
      for month in range(sm, em):
        print(str(cateid) + '-' + str(year) + '-' + str(month))
        insert(cateid, year, month)


if __name__ == '__main__':
  run(2017, 2018, 7, 9)
