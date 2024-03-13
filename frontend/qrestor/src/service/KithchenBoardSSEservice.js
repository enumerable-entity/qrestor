import { fetchEventSource } from '@microsoft/fetch-event-source';
import { useAuthStore } from '@/store.js'
class FatalError extends Error { }

const apiUrl = import.meta.env.VITE_ROOT_API
export default class KithchenBoardSSEservice {
  constructor() {
    this.url = apiUrl + '/kitchenboard/sse'
  }

  authHeader = () => {
    // return auth header with jwt if user is logged in and request is to the api url
    const { tokens: user } = useAuthStore()
    const isLoggedIn = !!user?.accessToken
    if (isLoggedIn) {
      return { Authorization: `Bearer ${user.accessToken}` }
    } else {
      return {}
    }
  }


  connect(callback) {
    fetchEventSource(this.url, {
      onmessage: (event) => {
        if (event.event === 'FatalError') {
          throw new FatalError(event.data);
        }
        callback(JSON.parse(event.data), event.event);
      },
      onopen: (event) => {
        console.log('EventSource connected:', event);
      },
      onerror(err) {
      if (err instanceof FatalError) {
        throw err; // rethrow to stop the operation
      } else {
        console.error('EventSource failed:', err);
      }
    },
      onclose: (err) => {
        throw err;
      },
      headers: {
        'Content-Type': 'text/event-stream',
        ...this.authHeader()}
    });
  }
}
