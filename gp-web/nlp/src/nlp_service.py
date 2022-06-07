from nlp import NLP
from nlp_pb2 import RpcNlpResponse
from nlp_pb2_grpc import RpcNlpServiceServicer

class NlpService(RpcNlpServiceServicer):
    def __init__(self,nlp_instance:NLP) -> None:
        self.nlp=nlp_instance
        super().__init__()

    def analysis(self, request, context):
        query = request.query
        print("recv query => %s" % query)
        return self.nlp.analysis(query)
