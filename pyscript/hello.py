# coding=utf-8
import sys  # 引入模块

def printHello():
      print 'Hello,' + \
            str + \
            '!'


_args = sys.argv  # 获取命令行参数
print _args  # 打印

pathArr = str(_args[0]).split('/');
printHello(pathArr[len(pathArr) - 1])

name = raw_input('who are you?\n')  # 标准输入
print 'Welcome ,', name

_personNames = ''
