package br.com.zup.chave.share

import br.com.zup.edu.KeyManagerConsultaGrpcServiceGrpc
import br.com.zup.edu.KeyManagerDeletaGrpcServiceGrpc
import br.com.zup.edu.KeyManagerGrpcServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class ChavePixClientFactory(@GrpcChannel("keyManager") val channel: ManagedChannel) {

    @Singleton
    fun createPixStub() = KeyManagerGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun deletePixStub() = KeyManagerDeletaGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun detailPixStub() = KeyManagerConsultaGrpcServiceGrpc.newBlockingStub(channel)
}