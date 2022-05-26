from concurrent import futures
import logging
import grpc
import nlp
import nlp_pb2_grpc
from nlp_service import NlpService

def start():
    server = grpc.server(futures.ThreadPoolExecutor(max_workers=4))
    nlp_pb2_grpc.add_RpcNlpServiceServicer_to_server(NlpService(),server)
    server.add_insecure_port('[::]:20880')
    server.start()
    logging.info("NLP Service Started On Port 20880")
    server.wait_for_termination()

if __name__ == '__main__':
    logging.basicConfig(level=logging.DEBUG)
    # start()
    nlp_instance=nlp.NLP()
