package br.com.zup.chave

import br.com.zup.edu.KeyManagerListaGrpcServiceGrpc
import br.com.zup.edu.ListaChavesPixRequest
import br.com.zup.edu.ListaChavesPixResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import java.util.*

@Controller("/api/v1/clientes/{clienteId}")
class ListaChavePixController(
    val grpcClient: KeyManagerListaGrpcServiceGrpc.KeyManagerListaGrpcServiceBlockingStub
) {

    @Get
    fun list(clienteId: UUID): HttpResponse<Any> {
        val response = grpcClient.lista(ListaChavesPixRequest.newBuilder()
            .setClienteId(clienteId.toString())
            .build())

        val chaves = response.chavesList.map { ListaChaves(it) }

        return HttpResponse.ok(chaves)
    }
}