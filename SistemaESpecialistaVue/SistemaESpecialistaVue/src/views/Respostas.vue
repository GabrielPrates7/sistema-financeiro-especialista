<template>
  <div class="container-fluid DivPrincipal">
    <div class="DivEsquerda text-break">
      <h2 style="display: flex; justify-content: center; align-items: center; padding-bottom: 50px;">
        Resultado do sistema:
      </h2>

      <p>Valor gastos fixos: R$ <span id="gastosFixosTexto"></span></p>
      <p>Quitação dívidas: R$ <span id="quitacaoTexto"></span></p>
      <p>Reserva emergência: R$ <span id="reservaTexto"></span></p>
      <p>Investimentos: R$ <span id="investimentosTexto"></span></p>
      <p>Objetivo futuro: R$ <span id="objetivoTexto"></span></p>
    </div>

    <div class="DivDireita">
  <div class="div3-1">
    <h2>Processamento concluído</h2>
  </div>

  <div class="p-3 border rounded bg-white m-3" v-if="carregando">
    Carregando respostas...
  </div>

  <div class="p-3 border rounded bg-white m-3 text-danger" v-if="erro">
    {{ erro }}
  </div>

  <!-- RESULTADO BONITO, SEM JSON -->
  <div v-if="resposta" class="p-3 border rounded bg-white m-3 sombraSuave">

    <h3 style="margin-bottom: 15px;">Resumo do processamento:</h3>

    <div class="resultado-card">
      <span class="titulo">Gastos fixos</span>
      <span class="valor">R$ {{ resposta.valorGastosFixos }}</span>
    </div>

    <div class="resultado-card">
      <span class="titulo">Quitação dívidas</span>
      <span class="valor">R$ {{ resposta.valorQuitacaoDividas }}</span>
    </div>

    <div class="resultado-card">
      <span class="titulo">Reserva emergência</span>
      <span class="valor">R$ {{ resposta.valorReservaEmergencia }}</span>
    </div>

    <div class="resultado-card">
      <span class="titulo">Investimentos</span>
      <span class="valor">R$ {{ resposta.valorInvestimentos }}</span>
    </div>

    <div class="resultado-card">
      <span class="titulo">Objetivo futuro</span>
      <span class="valor">R$ {{ resposta.valorObjetivo }}</span>
    </div>

  </div>

  <div class="div3-3">
    <input 
      class="btSeguinte"
      type="button"
      value="Voltar"
      @click="voltar"
    />
  </div>
</div>


  </div>
</template>

<script setup>
import axios from "axios"
import { ref, onMounted, nextTick } from "vue"
import { useRoute, useRouter } from "vue-router"

const route = useRoute()
const router = useRouter()

const resposta = ref(null)
const carregando = ref(true)
const erro = ref(null)

function digitarTexto(id, texto) {
  const el = document.getElementById(id)
  if (!el) return
  el.textContent = ""
  let i = 0
  const intervalo = setInterval(() => {
    el.textContent += texto[i] ?? ""
    i++
    if (i >= String(texto).length) clearInterval(intervalo)
  }, Math.floor(Math.random() * 30) + 20)
}

onMounted(async () => {
  try {
    const usuarioId = route.params.id
    console.log("Respostas.vue -> usuárioId:", usuarioId)

    // usa endpoint explícito por usuario
    const res = await axios.get(`http://localhost:8080/respostas-sistema/usuario/${usuarioId}`)
    console.log("Resposta do backend:", res)

    // checa conteúdo esperado
    if (!res || !res.data) {
      erro.value = "Resposta vazia do servidor."
      return
    }

    resposta.value = res.data
    await nextTick()

    // preenche o lado esquerdo com o efeito de digitação
    digitarTexto("gastosFixosTexto", String(resposta.value.valorGastosFixos ?? "0"))
    digitarTexto("quitacaoTexto", String(resposta.value.valorQuitacaoDividas ?? "0"))
    digitarTexto("reservaTexto", String(resposta.value.valorReservaEmergencia ?? "0"))
    digitarTexto("investimentosTexto", String(resposta.value.valorInvestimentos ?? "0"))
    digitarTexto("objetivoTexto", String(resposta.value.valorObjetivo ?? "0"))

  } catch (err) {
    console.error("Erro ao carregar resposta:", err)
    // detalha erro para você ver no UI
    if (err.response) {
      erro.value = `Erro ${err.response.status}: ${err.response.data || err.response.statusText}`
    } else {
      erro.value = "Erro de conexão com o servidor."
    }
  } finally {
    carregando.value = false
  }
})

function voltar() {
  router.push("/sistema")
}
</script>
