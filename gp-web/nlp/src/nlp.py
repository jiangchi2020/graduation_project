import jieba
import numpy
import logging
from sklearn.naive_bayes import BernoulliNB

from keyword import Keyword

def load_map(file:str,split:str)->dict:
    f=open(file)
    line=f.readline()
    map={}
    while line:
        line = line.replace('\r','').replace('\n','')
        e=line.split(split)
        map[e[0]]=int(e[1])
        line=f.readline()
    f.close()
    return map

def load_predicate(file:str)->dict:
    f=open(file)
    line=f.readline()
    map={}
    i=0
    while line:
        line = line.replace('\r','').replace('\n','')
        map[line]=i
        i=i+1
        line=f.readline()
    f.close()
    return map

def load_name_set(file:str)->set:
    f=open(file)
    result=set()
    line=f.readline()
    while line:
        result.add(line.replace('\r','').replace('\n',''))
        line=f.readline()
    f.close()
    return result

class NLP(object):
    def __init__(self):
        # 初始化jieba、bayes、和关键字匹配数据集
        logging.info("加载词典数据...")
        jieba.load_userdict("../dataset/word.txt")
        self.jieba=jieba
        logging.info("加载兴趣点类别编码映射...")
        self.poi_code_map=load_map("../dataset/poi_code_map.txt","=")
        logging.debug(self.poi_code_map)
        logging.info("加载铁路数据集...")
        self.railway_set=load_name_set("../dataset/railway_name.txt")
        logging.debug(self.railway_set)
        logging.info("加载车站数据集...")
        self.station_set=load_name_set("../dataset/station_name.txt")
        logging.debug(self.station_set)
        logging.info("加载默认距离描述谓词映射...")
        self.distance_map=load_map("../dataset/distance_predicate_map.txt","=")
        logging.debug(self.distance_map)
        logging.info("加载拓扑关系谓词...")
        self.predicate_map=load_predicate("../dataset/predicate.txt")
        logging.debug(self.predicate_map)
        logging.info("加载NLP训练数据集...")
        self.bayes=BernoulliNB()
        f=open("../dataset/training_set.txt")
        line=f.readline()
        while line:
            if(line.startswith("#")):
                continue
            line=line.replace('\r','').replace('\n','')
            _line=line.split("=")
            seq=_line[0]
            clazz=int(_line[1])
            # 训练数据处理逻辑
            words=self.jieba.cut(seq)

            line=f.readline()
        f.close()
    
    def analysis(self,query:str)->Keyword:
        words=self.jieba.cut(query)



# from sklearn.naive_bayes import BernoulliNB
# import numpy as np
# 
# #               沿线，站，附近，周边，边上，公里，米，里，海里，华里
# # 线-精确距离：     1   0  0     0    0    1  0   0   0     0
# # 线-模糊距离：
# # 点-精确距离：
# # 点-模糊距离：
# 
# bayes=BernoulliNB()
# bayes.fit(
#     np.array([
#         [1,0,0,0,0,1,0,0,0,0],
#         [1,0,0,0,0,0,1,0,0,0],
#         [1,0,0,0,0,0,0,1,0,0],
#         [1,0,0,0,0,0,0,0,1,0],
#         [1,0,0,0,0,0,0,0,0,1],
#         [1,0,1,0,0,0,0,0,0,0],
#         [1,0,0,1,0,0,0,0,0,0],
#         [1,0,0,0,1,0,0,0,0,0],
#         [0,1,0,0,0,1,0,0,0,0],
#         [0,1,0,0,0,0,1,0,0,0],
#         [0,1,0,0,0,0,0,1,0,0],
#         [0,1,0,0,0,0,0,0,1,0],
#         [0,1,0,0,0,0,0,0,0,1],
#         [0,1,1,0,0,0,0,0,0,0],
#         [0,1,0,1,0,0,0,0,0,0],
#         [0,1,0,0,1,0,0,0,0,0],
#     ]),
#     np.array([1,1,1,1,1,2,2,2,3,3,3,3,3,4,4,4])
# )
# p=[[1,0,0,1,0,0,1,0,0,0]]
# print(bayes.predict_proba(p))