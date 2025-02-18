<script setup>
import { ref } from 'vue'

import PollOption from './components/PollOption.vue';

let weaps = ["mace", "flail", "sword", "warhammer"]
const chosen = ref('')
const poll = ref(null)
const pollResults = ref(null)


async function fetchPoll() {
  poll.value = null

  const response = await fetch('http://localhost:3000/poll/current')
  const data = await response.json()

  poll.value = data
}

async function submitVote() {

}

async function fetchPollResults() {
  pollResults.value = null

  const response = await fetch('http://localhost:3000/poll/results', {mode: 'no-cors'})
  const data = await response.json()

  pollResults.value = data
}

fetchPoll()
</script>

<template>
  <h1>Poll</h1>

  <p v-if="!poll">Fetching poll details</p>
  <p v-else-if="poll">Poll: {{ poll.value }}</p>


  <!-- <div>
    
    <h1>What's your favourite  weapon?</h1>

    <p>Chosen:</p>
    <p>{{ chosen }}</p>

    <ul>
      <li v-for="weap in weaps">
        <PollOption @weapon="(msg) => chosen = msg" :text="weap" :enabled="!chosen" :totalVotes=10 :chosenVotes=2 :key="weap"></PollOption>
      </li>
    </ul>
  </div> -->


</template>

<style scoped>
ul {
  list-style-type: none;
  padding: 0;
}

li {
  margin: 10px;
}
</style>
