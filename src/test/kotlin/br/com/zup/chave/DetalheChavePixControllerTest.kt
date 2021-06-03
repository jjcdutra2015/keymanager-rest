package br.com.zup.chave

import br.com.zup.chave.share.ChavePixClientFactory
import br.com.zup.edu.ConsultaChavePixResponse
import br.com.zup.edu.KeyManagerConsultaGrpcServiceGrpc.KeyManagerConsultaGrpcServiceBlockingStub
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.*
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class DetalheChavePixControllerTest {

    @field:Inject
    lateinit var grpcClient: KeyManagerConsultaGrpcServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    fun `deve consultar chave pix e retornar os detalhes`() {
        val clienteId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        val grpcRestposta = ConsultaChavePixResponse.newBuilder()
            .setPixId(pixId)
            .setClienteId(clienteId)
            .build()

        given(grpcClient.consulta(any())).willReturn(grpcRestposta)

        val request = HttpRequest.GET<Any>("http://localhost:8080/api/v1/clientes/$clienteId/pix/$pixId")
        val response = client.toBlocking().exchange(request, Any::class.java)

        assertEquals(HttpStatus.OK, response.status)
    }

    @Factory
    @Replaces(factory = ChavePixClientFactory::class)
    internal class MockClientStub() {
        @Singleton
        fun clientMockStub() = mock(KeyManagerConsultaGrpcServiceBlockingStub::class.java)
    }
}