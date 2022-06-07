from pickle import TRUE
import jieba
import numpy as np
import logging
from sklearn.naive_bayes import BernoulliNB

from keyword import Keyword
from keyword import Clazz
from keyword import QueryType

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

def extract_numbers(seq:str):
    return 1

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
        x=[]
        y=[]
        while line:
            if(line.startswith("#")):
                continue
            line=line.replace('\r','').replace('\n','')
            _line=line.split("=")
            seq=_line[0]
            clazz=int(_line[1])
            # 训练数据处理逻辑
            words=self.jieba.cut(seq)
            xi=[0]*len(self.predicate_map)
            for word in words:
                if word in self.predicate_map.keys():
                    index=self.predicate_map[word]
                    xi[index]=1
            x.append(xi)
            y.append(clazz)
            line=f.readline()
        f.close()
        self.bayes.fit(np.array(x),np.array(y))

    def classify(self,words)->Clazz:
        xi=[0]*len(self.predicate_map)
        for word in words:
            if word in self.predicate_map.keys():
                index=self.predicate_map[word]
                xi[index]=1
        clazz = self.bayes.predict(xi)
        if clazz==0:
            return Clazz(QueryType.LINE,True)
        elif clazz==1:
            return Clazz(QueryType.LINE,False)
        elif clazz==2:
            return Clazz(QueryType.POINT,True)
        elif clazz==3:
            return Clazz(QueryType.POINT,False)
        else:
            raise Exception("classify error")
    
    def analysis(self,query:str)->Keyword:
        words=self.jieba.cut(query)
        clazz:Clazz = self.classify(words=words)
        result=Keyword()

        if(clazz.query_type==QueryType.LINE):
            # 在铁路数据集中匹配
            for word in words:
                if word in self.railway_set:
                    result.center=word
                    result.type=QueryType.LINE
                    break
        elif(clazz.query_type==QueryType.POINT):
            # 在车站数据集中匹配
            for word in words:
                if word in self.station_set:
                    result.center=word
                    result.type=QueryType.POINT
                    break
        
        number=1
        if(clazz.is_accurate):
            # 提取文本数字
            number=extract_numbers(query)
        for word in words:
            if word in self.distance_map:
                result.distance = number*self.distance_map[word]
        for word in words:
            if word in self.poi_code_map:
                result.poiCode = self.poi_code_map[word]
        return result

        
