package br.com.zup.chave

import br.com.zup.chave.share.ChavePixClientFactory
import br.com.zup.edu.DeletaChavePixResponse
import br.com.zup.edu.KeyManagerDeletaGrpcServiceGrpc.KeyManagerDeletaGrpcServiceBlockingStub
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.any
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class DeletaChavePixControllerTest {

    @field:Inject
    lateinit var grpcClient: KeyManagerDeletaGrpcServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    fun `deve remover uma chave pix`() {
        val clienteId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        val respostaGrpc = DeletaChavePixResponse.newBuilder()
            .setPixId(pixId)
            .setClienteId(clienteId)
            .build()

        given(grpcClient.deleta(any())).willReturn(respostaGrpc)

        val request = HttpRequest.DELETE<Any>("/api/v1/clientes/${clienteId}/pix/${pixId}")
        val response = client.toBlocking().exchange(request, Any::class.java)

        Assertions.assertEquals(HttpStatus.OK, response.status)
    }

    @Factory
    @Replaces(factory = ChavePixClientFactory::class)
    internal class MockClientStub {
        @Singleton
        fun mockStub() = mock(KeyManagerDeletaGrpcServiceBlockingStub::class.java)
    }
}