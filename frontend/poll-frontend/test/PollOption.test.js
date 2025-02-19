import { test, expect } from 'vitest'
import PollOption from '../src/components/PollOption.vue'
import { mount } from '@vue/test-utils'

test(`PollOption.vue renders correctly`, () => {
    const wrapper = mount(PollOption, {
        props: {
            text: "choice",
            enabled: true,
            totalVotes: 0,
            chosenVotes: 0
        }
    })

    expect(wrapper.findAll("button")).toHaveLength(1)
    expect(wrapper.text()).toContain(("choice"))
})

test(`PollOption.vue emits 'choice' event when button is clicked`, () => {
    const wrapper = mount(PollOption, {
        props: {
            text: "choice",
            enabled: true,
            totalVotes: 0,
            chosenVotes: 0
        }
    })

    wrapper.find("button").trigger("click")
    expect(wrapper.emitted()).toHaveProperty("choice")
    const choiceEvent = wrapper.emitted("choice")
    expect(choiceEvent[0]).toStrictEqual(["choice"])
})



