class Keyword():
    def __init__(self) -> None:
        self.type=QueryType.UNDEFINED
        self.center=""
        self.distance=0
        self.poiCode=0

class QueryType():
    POINT = 1
    LINE = 2
    UNDEFINED = 3

class Clazz():
    def __init__(self,type:QueryType,distance_type:bool) -> None:
        self.query_type=type
        self.is_accurate=distance_type