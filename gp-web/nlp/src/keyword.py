class Keyword(object):
    def __init__(self) -> None:
        self.type=QueryType.UNDEFINED
        self.center=""
        self.distance=0
        self.poiCode=0

class QueryType():
    POINT = 1
    LINE = 2
    UNDEFINED = 3
