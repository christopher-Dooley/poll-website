import { test, expect, afterAll, afterEach, beforeAll } from 'vitest'
import App from '../src/App.vue'
import { mount, flushPromises } from '@vue/test-utils'
import { setupServer } from 'msw/node'
import { http, HttpResponse } from 'msw'

const mockPoll = {
    uuid: "uuid",
    name: "name",
    question: "question",
    options: [ 
        "option1", "option2"
    ],
    totalVotes: 0
}

const restHandlers = [
    http.get('http://localhost:3000/poll/current', () => {
      return HttpResponse.json(mockPoll)
    }),
]
  
const server = setupServer(...restHandlers)

beforeAll(() => server.listen({ onUnhandledRequest: 'error' }))
afterAll(() => server.close())
afterEach(() => server.resetHandlers())

test(`App.vue renders correctly`, () => {
    const wrapper = mount(App)

    expect(wrapper.text()).toContain("Fetching poll details")
})

test(`App.vue fetches poll details`, async () => {
    const wrapper = mount(App)

    await flushPromises()

    expect(wrapper.text()).toContain("name")
    expect(wrapper.text()).toContain("question")
    expect(wrapper.text()).toContain("option1")
    expect(wrapper.text()).toContain("option2")

    const pollOptions = wrapper.findAll("li")
    expect(pollOptions).toHaveLength(2)
    expect(pollOptions[0].text()).toContain("option1")
    expect(pollOptions[1].text()).toContain("option2")
})