# coding=utf-8
import urllib2
import gzip
import time
import datetime
import calendar

from bs4 import BeautifulSoup
from StringIO import StringIO

HEADER = {
  'User-Agent': 'Mozilla/5.0 (Windows U Windows NT 6.1 en-US rv:1.9.1.6) Gecko/20091201 Firefox/3.5.6'}


def content(url):
  while True:
    try:
      req = urllib2.Request(url=url, headers=HEADER)
      page = urllib2.urlopen(req)
      _content = page.read()
      if page.info().get('Content-Encoding') == 'gzip':
        buf = StringIO(_content)
        _content = gzip.GzipFile(fileobj=buf).read()
      return _content
    except urllib2.HTTPError, e:
      if e.code == 404:
        return ""
      time.sleep(5)


def soap(url):
  return BeautifulSoup(content(url), 'html.parser')


def yyyyMM(year, month):
  today = datetime.date(year=year, month=month, day=1)
  return today.strftime('%Y%m')

def monthrange_yyyyMMdd(year, month):
  today = datetime.date(year=year, month=month, day=1)
  first_day, last_day = calendar.monthrange(today.year, today.month)
  return today.strftime('%Y%m01'), today.strftime('%Y%m' + str(last_day))


def yearrange_yyyyMMdd(year):
  today = datetime.date(year=year)
  return today.strftime('%Y0101'), today.strftime('%Y1231')
