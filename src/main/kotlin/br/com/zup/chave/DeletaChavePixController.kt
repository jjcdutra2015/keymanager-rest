package br.com.zup.chave

import br.com.zup.edu.DeletaChavePixRequest
import br.com.zup.edu.KeyManagerDeletaGrpcServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import java.util.*

@Controller("/api/v1/clientes/{clienteId}/pix/{pixId}")
class DeletaChavePixController(
    val grpcClient: KeyManagerDeletaGrpcServiceGrpc.KeyManagerDeletaGrpcServiceBlockingStub
) {

    @Delete
    fun delete(clienteId: UUID, pixId: UUID): HttpResponse<Any> {
        grpcClient.deleta(
            DeletaChavePixRequest.newBuilder()
                .setPixId(pixId.toString())
                .setClienteId(clienteId.toString())
                .build()
        )

        return HttpResponse.ok()
    }
}