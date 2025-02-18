<script setup>
import { computed, ref } from 'vue'

const props = defineProps({
    text: String,
    enabled: Boolean,
    totalVotes: Number,
    chosenVotes: Number
})

const chosen = ref(false)

const emit = defineEmits(['choice'])

function chooseOption(text) {
    emit("choice", text)
    chosen.value = true
}

const fillPercentage = computed(() => {
    return props.totalVotes == 0 ? 0 : Math.min(100, props.chosenVotes/props.totalVotes * 100)
})

const buttonClass = computed(() => {
    return chosen.value ? 'option chosen' : 'option'
})

</script>

<template>

    <button :class="buttonClass" type="button" @click="chooseOption(props.text)" :disabled="!props.enabled">
        <div class="fill" :style="{ width: fillPercentage + '%' }"></div>
        <p>{{ props.text }}</p>
    </button>

</template>

<style scoped>
.option {
    position: relative;
    width: 100%;
    height: 80px;
    border: 1px solid #ccc;
    overflow: hidden;
    background-color: #f9f9f9;
}

.option.chosen {
    border-color: #ff9800;
    border-width: medium;
}

.fill {
    position: absolute;
    top: 0;
    left: 0;
    height: 100%;
    background-color: #4caf50;
    z-index: 1;
}

p {
    position: relative;
    z-index: 2;
    margin: 0;
    font-size: 20px;
    font-weight: bold;
}
</style>