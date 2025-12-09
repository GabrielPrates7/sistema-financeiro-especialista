<template>

  <div class="container-fluid text-center DivPrincipal">

    <div class="row DivSecundariaCima">

      <div class="DivTerceira">
        <p class="montserrat">Vortex Invest</p>
        <transition name="slide-fade" mode="out-in">
          <p :key="currentIndex" class="lato-regular">{{ content[currentIndex].text }}</p>
        </transition>
      </div>

      <div class="DivTerceira">
        <button type="button" class="btn btn-outline-primary btAcesso" @click="AcessoSistema">Acessar Sistema</button>
      </div>


    </div>

    <!-- <div class="row DivSegundariaBaixo">

      <div class="col">
        <p style="text-align: left;" class="lato-regular-baixo">
          Bem-vindo ao Vortex Invest, seu novo especialista em finanças.
          Nossa plataforma foi desenhada para simplificar o mundo dos investimentos.
          Você insere suas metas, detalha seus gastos e informa suas sobras mensais.
          A partir daí, nossa inteligência analisa seu perfil e apresenta um leque de alternativas personalizadas para
          que seu dinheiro comece a trabalhar por você.
          Investir nunca foi tão intuitivo.
        </p>
      </div>

      <div class="col ">
        <img src="/src/components/seguro-de-investimento.png"></img>
      </div>

    </div>-->

  </div>

</template>


<script setup>

import { ref } from 'vue';
import { onMounted, onUnmounted } from "vue";
import { useRouter } from 'vue-router'

const router = useRouter()

function AcessoSistema(){
   router.push('/sistema')
}

onMounted(() => {
  window.addEventListener("wheel", handleScroll);
});

onUnmounted(() => {
  window.removeEventListener("wheel", handleScroll);
});


// Array com os conteúdos que irão alternar.
// O primeiro item é o seu texto original. Adicione mais objetos a este array para criar mais "páginas" de scroll.
const content = ref([
  {
    text: `Seu Sistema Especialista de Investimentos`,
  },
  {
    text: 'Com Simples Cliques',
  },
  {
    text: 'Veja as Melhores Opções de Insvestimentos para Você !',
  }
]);

// `currentIndex` rastreia qual item do array `content` está sendo exibido.
const currentIndex = ref(0);
// `isScrolling` é uma flag para evitar que o evento de scroll seja disparado várias vezes durante a animação.
const isScrolling = ref(false);

const handleScroll = (event) => {
  if (isScrolling.value) return;

  isScrolling.value = true;

  if (event.deltaY > 0) {
    // se for o último, volta pro primeiro
    if (currentIndex.value < content.value.length - 1) {
      currentIndex.value++;
    } else {
      currentIndex.value = 0; // reinicia
    }
  } else {
    // se for o primeiro, volta pro último
    if (currentIndex.value > 0) {
      currentIndex.value--;
    } else {
      currentIndex.value = content.value.length - 1; // último
    }
  }

  setTimeout(() => {
    isScrolling.value = false;
  }, 800);
};

</script>