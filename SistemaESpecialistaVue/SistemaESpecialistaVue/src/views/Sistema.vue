<template>
  <div class="container-fluid DivPrincipal">
    <div class="DivEsquerda text-break">
      <h2 style="display: flex; justify-content: center; align-items: center; padding-bottom: 50px;">
        Resumo da aplicação:
      </h2>

      <p>Salário: R$ <span id="salarioTexto"></span></p>
      <p>Gastos fixos: R$ <span id="gastoFixoTexto"></span></p>
      <p>Perfil: <span id="perfilTexto"></span></p>
      <p>Quantidade filhos: <span id="filhosTexto"></span></p>
      <p>Dividas: R$ <span id="dividasTexto"></span></p>
      <p>Reserva: R$ <span id="reservaTexto"></span></p>
      <p>Objetivo: <span id="objetivoTexto"></span></p>
    </div>

    <div class="DivDireita">
      <div class="div3-1">
        <h2>Preencha o formulário</h2>
      </div>

      <div class="div3-2-container">
        <div class="div3-2">

          <input v-model="salario" class="form-check my-3 p-3 border rounded" type="text" placeholder="Salário:">
          <input v-model="gastoFixo" class="form-check my-3 p-3 border rounded" type="text" placeholder="Gastos fixos:">

          <label class="my-2 p-2">Tipo de perfil:</label>
          <div class="div3-2-radio">
            <input type="radio" id="perfil-conservador" value="conservador" v-model="perfil" name="perfil">
            <label for="perfil-conservador" class="form-check my-3 p-3 border rounded labelBtn1">conservador</label>

            <input type="radio" id="perfil-moderado" value="moderado" v-model="perfil" name="perfil">
            <label for="perfil-moderado" class="form-check my-3 p-3 border rounded labelBtn2">moderado</label>

            <input type="radio" id="perfil-arrojado" value="arrojado" v-model="perfil" name="perfil">
            <label for="perfil-arrojado" class="form-check my-3 p-3 border rounded labelBtn3">arrojado</label>
          </div>

          <input v-model="qtdFilhos" class="form-check my-3 p-3 border rounded" type="text"
            placeholder="Quantidade de filhos:">
          <input v-model="dividas" class="form-check my-3 p-3 border rounded" type="text"
            placeholder="Valor em dívidas:">
          <input v-model="reserva" class="form-check my-3 p-3 border rounded" type="text"
            placeholder="Reserva de emergência:">
          <input v-model="objetivo" class="form-check my-3 p-3 border rounded" type="text"
            placeholder="Objetivo futuro:">
        </div>
      </div>

      <!-- ÁREA QUE EXIBE O RESULTADO -->
      <div v-if="resultado" class="p-3 border rounded bg-white m-3">
        <h3>Resultado:</h3>
        <pre>{{ resultado }}</pre>
      </div>

      <div class="div3-3">
        <input 
          class="btSeguinte" 
          type="button" 
          :value="botaoTexto" 
          @click="handleClick" 
          v-if="todosPreenchidos" 
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from "axios"
import { ref, watch, computed, nextTick } from 'vue'
import { useRouter } from "vue-router"

const router = useRouter()

const usuarioId = ref(null)
const salario = ref("")
const gastoFixo = ref("")
const perfil = ref("")
const qtdFilhos = ref("")
const dividas = ref("")
const reserva = ref("")
const objetivo = ref("")

const botaoTexto = ref("Enviar")
const resultado = ref(null)

// ------------------------------
// Função para chamada POST → /enviar
// ------------------------------
async function enviarRespostas() {
  try {
    const payload = {
      salario: Number(salario.value),
      gastoFixo: Number(gastoFixo.value),
      perfil: perfil.value,
      filhos: Number(qtdFilhos.value),
      divida: Number(dividas.value),
      reserva: Number(reserva.value),
      objetivo: objetivo.value
    }

    const salvar = await axios.post("http://localhost:8080/users", payload)
    
    usuarioId.value = salvar.data.id
    botaoTexto.value = "Resultado"

  } catch (err) {
    console.error(err)
    alert("Erro ao enviar respostas.")
  }
}

// ------------------------------
// Função que abre a tela de resposta
// ------------------------------
function verResultado() {
  if (!usuarioId.value) {
    alert("Envie os dados primeiro!")
    return
  }
  router.push(`/respostas-sistema/${usuarioId.value}`)
}

// ------------------------------
// Clique do botão — alterna POST/GET
// ------------------------------
async function handleClick() {
  if (botaoTexto.value === "Enviar") {
    await enviarRespostas()
  } else {
    verResultado()
  }
}

// ------------------------------
// Função de digitação suave
// ------------------------------
const timeouts = {}
function digitarTexto(id, texto) {
  const el = document.getElementById(id)
  if (!el) return

  if (!texto) {
    el.textContent = ""
    return
  }

  if (timeouts[id]) clearTimeout(timeouts[id])

  timeouts[id] = setTimeout(() => {

    if (el.intervalo) clearInterval(el.intervalo)

    el.textContent = ""

    let i = 0

    el.intervalo = setInterval(() => {
      el.textContent += texto[i]
      i++
      if (i >= texto.length) clearInterval(el.intervalo)
    }, Math.floor(Math.random() * 30) + 30)
  }, 2500)
}

// ------------------------------
// Observadores dos campos
// ------------------------------
watch(salario, (novo) => nextTick(() => digitarTexto("salarioTexto", novo)))
watch(gastoFixo, (novo) => nextTick(() => digitarTexto("gastoFixoTexto", novo)))
watch(perfil, (novo) => nextTick(() => digitarTexto("perfilTexto", novo)))
watch(qtdFilhos, (novo) => nextTick(() => digitarTexto("filhosTexto", novo)))
watch(dividas, (novo) => nextTick(() => digitarTexto("dividasTexto", novo)))
watch(reserva, (novo) => nextTick(() => digitarTexto("reservaTexto", novo)))
watch(objetivo, (novo) => nextTick(() => digitarTexto("objetivoTexto", novo)))

// ------------------------------
// Validação: botão só aparece se tudo estiver preenchido
// ------------------------------
const todosPreenchidos = computed(() => {
  return (
    salario.value &&
    gastoFixo.value &&
    perfil.value &&
    qtdFilhos.value &&
    dividas.value &&
    reserva.value &&
    objetivo.value
  )
})
</script>
