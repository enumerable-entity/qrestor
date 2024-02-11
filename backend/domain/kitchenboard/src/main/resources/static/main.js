import { EventStreamContentType, fetchEventSource } from './fetch.js';
class RetriableError extends Error { }
class FatalError extends Error { }
const ctrl = new AbortController();
window.startFetching = async function() {
    console.log('After import');
    await fetchEventSource('/sse', {
        signal: ctrl.signal,
        async onopen(response) {
            if (response.ok && response.headers.get('content-type') === EventStreamContentType) {
                return; // everything's good
            } else if (response.status >= 400 && response.status < 500 && response.status !== 429) {
                // client-side errors are usually non-retriable:
                throw new FatalError();
            } else {
                throw new RetriableError();
            }
        },
        headers: {
            'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJyb2xlcyI6WyJSRVNUQVVSQVRFVVIiXSwic3ViIjoiTmV3VXNlclJlZ2lzdHJlZCIsImp0aSI6IjljYmJlZTdkLWIwMjYtNDY5ZC05MDNmLTM0MjlmZDBiNGZjMiIsImlhdCI6MTcwNzU5Njk3MiwiZXhwIjoxNzA4MjAxNzcyLCJpc3MiOiJhdXRoIn0.qLfJAETT1q0n89JTYdKWvAB0i2A4LRgTenDI6qqvfkfqPnjImQXTPnaAHLpRhvE_Gt5jEdG6Qk7QL5n9PRNuSQ',
            'Content-Type': 'text/event-stream'
        },

        onclose() {
            // if the server closes the connection unexpectedly, retry:
            throw new RetriableError();
        },
        onmessage(ev) {
            // if the server emits an error message, throw an exception
            // so it gets handled by the onerror callback below:
            if (ev.event === 'FatalError') {
                throw new FatalError(ev.data);
            }
            if(ev.event === "ordersUpdate"){
                console.log("New order update:", ev.data);
            }
            console.log(ev);
        },
        onerror(err) {
            if (err instanceof FatalError) {
                throw err; // rethrow to stop the operation
            } else {
                // do nothing to automatically retry. You can also
                // return a specific retry interval here.
            }
        }
    });
};