package br.com.zup.chave

import br.com.zup.chave.share.ChavePixClientFactory
import br.com.zup.edu.*
import br.com.zup.edu.KeyManagerConsultaGrpcServiceGrpc.*
import com.google.protobuf.Timestamp
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito
import org.mockito.BDDMockito.*
import org.mockito.Mockito
import org.mockito.Mockito.mock
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class ListaChavePixControllerTest {

    @field:Inject
    lateinit var grpcClient: KeyManagerListaGrpcServiceGrpc.KeyManagerListaGrpcServiceBlockingStub

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    fun `deve listar todas as chaves pix cadastradas`() {
        val clienteId = UUID.randomUUID().toString()

        val cpf = ListaChavesPixResponse.ChavesPix.newBuilder()
            .setPixId(clienteId)
            .setTipoDeChave(TipoDeChave.CPF)
            .setChave("11111111111")
            .setTipoDeConta(TipoDeConta.CONTA_CORRENTE)
            .setRegistradaEm(LocalDateTime.now().let {
                val createdAt = it.atZone(ZoneId.of("UTC")).toInstant()
                Timestamp.newBuilder()
                    .setSeconds(createdAt.epochSecond)
                    .setNanos(createdAt.nano)
                    .build()
            })
            .build()

        val respostaGrpc = ListaChavesPixResponse.newBuilder()
            .addAllChaves(listOf(cpf))
            .build()

        given(grpcClient.lista(Mockito.any())).willReturn(respostaGrpc)

        val request = HttpRequest.GET<Any>("/api/v1/clientes/$clienteId")
        val response = client.toBlocking().exchange(request, List::class.java)

        assertEquals(HttpStatus.OK, response.status)
    }

    @Factory
    @Replaces(factory = ChavePixClientFactory::class)
    internal class MockClientStub {
        @Singleton
        fun clientStub() = mock(KeyManagerConsultaGrpcServiceBlockingStub::class.java)
    }
}