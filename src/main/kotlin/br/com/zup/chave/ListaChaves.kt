package br.com.zup.chave

import br.com.zup.edu.ListaChavesPixResponse
import io.micronaut.core.annotation.Introspected
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Introspected
class ListaChaves(response: ListaChavesPixResponse.ChavesPix) {

        val pixId = response.pixId
        val clienteId = response.clienteId
        val tipoDeChave = response.tipoDeChave
        val chave = response.chave
        val tipoDeConta = response.tipoDeConta
        val criadaEm = response.registradaEm.let {
                LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneId.of("UTC"))
        }
}
