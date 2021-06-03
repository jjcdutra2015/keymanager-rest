package br.com.zup.chave

import br.com.zup.edu.ConsultaChavePixRequest
import br.com.zup.edu.ConsultaChavePixResponse
import br.com.zup.edu.KeyManagerConsultaGrpcServiceGrpc.KeyManagerConsultaGrpcServiceBlockingStub
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import java.util.*

@Controller("/api/v1/clientes/{clienteId}")
class DetalheChavePixController(
    private val grpcClient: KeyManagerConsultaGrpcServiceBlockingStub
) {
    @Get("/pix/{pixId}")
    fun detail(clienteId: UUID, pixId: UUID): HttpResponse<Any> {
        val response = grpcClient.consulta(ConsultaChavePixRequest.newBuilder()
            .setPixId(ConsultaChavePixRequest.FiltroPorPixId.newBuilder()
                .setClienteId(clienteId.toString())
                .setPixId(pixId.toString())
                .build())
            .build())

        return HttpResponse.ok(DetalheChavePixResponse(response))
    }
}