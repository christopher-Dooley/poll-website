<script setup>
import { ref, watch } from 'vue'

import PollOption from './components/PollOption.vue';

const chosen = ref(null)
const poll = ref(null)
const pollResults = ref(null)

async function fetchPoll() {
  poll.value = null

  try {
    const response = await fetch('http://localhost:3000/poll/current')
    const data = await response.json()
    poll.value = data
  } catch (error) {
    console.error('Error fetching poll:', error)
  }
}

async function fetchPollResults() {
  pollResults.value = null

  try {
    const response = await fetch('http://localhost:3000/poll/current/results')
    const data = await response.json()
    pollResults.value = data
    console.log(data)
  } catch (error) {
    console.error('Error fetching poll results:', error)
  }
}

async function submitVote(choice) {
  try {
    await fetch('http://localhost:3000/poll/vote/save', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        pollName: poll.value.name,
        option: choice,
        timestamp: getZonedDateTimeString()
      })
    })
  } catch (error) {
    console.error('Error submitting vote:', error)
  }
}

function getZonedDateTimeString(date = new Date()) {
  const offset = date.getTimezoneOffset(); 
  const sign = offset >= 0 ? "+" : "-";
  const pad = (n) => String(Math.floor(Math.abs(n))).padStart(2, "0");

  const hours = pad(offset / 60); 
  const minutes = pad(offset % 60); 

  const zone = "[" + Intl.DateTimeFormat().resolvedOptions().timeZone + "]"

  return date.toISOString().replace("Z", `${sign}${hours}:${minutes}`) + zone;
}

watch(chosen, async (newChoice) => {
  if (newChoice) {
    await submitVote(newChoice)
    fetchPollResults()
  }
})

fetchPoll()
</script>

<template>
  <p v-if="!poll">Fetching poll details</p>

  <div class="container" v-else>
    <h1>{{ poll.name }}</h1>
    <h1>{{ poll.question }}</h1>

    <ul>
      <li v-for="choice in poll.options">
        <PollOption @choice="(msg) => chosen = msg" :text="choice" :enabled="!chosen" :totalVotes="pollResults?.totalVotes || 0" :chosenVotes="pollResults?.results[choice] || 0" ></PollOption>
      </li>
    </ul>
  </div>  

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
