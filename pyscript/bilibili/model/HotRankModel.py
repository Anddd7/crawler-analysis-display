# coding=utf-8
CATALOGY_URL = 'https://www.bilibili.com/index/catalogy/'

DAY = '1day'
THREE_DAY = '3day'
WEEK = 'week'


class HotRankModel:
  def __init__(self, cate_id='17', rank_type=THREE_DAY):
    self.path = CATALOGY_URL + '-'.join([cate_id, rank_type]) + '.json'
