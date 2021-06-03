package br.com.zup.chave

import br.com.zup.edu.ConsultaChavePixResponse
import br.com.zup.edu.TipoDeConta
import io.micronaut.core.annotation.Introspected
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Introspected
class DetalheChavePixResponse(response: ConsultaChavePixResponse) {
    val clienteId = response.clienteId
    val pixId = response.pixId
    val tipoChave = response.chave.tipoDeChave
    val chave = response.chave.chave

    val tipoConta = when(response.chave.info.tipoDeConta) {
        TipoDeConta.CONTA_CORRENTE -> "CONTA_CORRENTE"
        TipoDeConta.CONTA_POUPANCA -> "CONTA_POUPANCA"
        else -> "CONTA_DESCONHECIDA"
    }

    val conta = mapOf(
        Pair("tipo", response.chave.info.tipoDeConta),
        Pair("instituicao", response.chave.info.instituicao),
        Pair("nomeDoTitular", response.chave.info.nomeDoTitular),
        Pair("cpfDoTitular", response.chave.info.cpfDoTitular),
        Pair("agencia", response.chave.info.agencia),
        Pair("numero", response.chave.info.numero),
    )

    val criadaEm = response.chave.criadaEm.let {
        LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneId.of("UTC"))
    }
}
