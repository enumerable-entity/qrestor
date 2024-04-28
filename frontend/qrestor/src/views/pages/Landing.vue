<script setup>
import { useLayout } from '@/layout/composables/layout'
import { computed, ref, onMounted } from 'vue'
import AppConfig from '@/layout/AppConfig.vue'
import { useAuthStore } from '@/store'

const authStore = useAuthStore()

const { layoutConfig } = useLayout()

const smoothScroll = (id) => {
  document.querySelector(id).scrollIntoView({
    behavior: 'smooth'
  })
}

const loginLabel = ref('')

onMounted(() => {
  loginLabel.value = authStore.isAuthenticated() ? 'Dashboard' : 'Login'
})

const logoUrl = computed(() => {
  return `layout/images/${layoutConfig.darkTheme.value ? 'logo-white' : 'logo-dark'}.svg`
})
</script>

<template>
  <div class="surface-0 flex justify-content-center">
    <div id="home" class="landing-wrapper overflow-hidden">
      <div
        class="py-4 px-4 mx-0 md:mx-6 lg:mx-8 lg:px-8 flex align-items-center justify-content-between relative lg:static mb-3"
      >
        <a class="flex align-items-center" href="#">
          <img :src="logoUrl" alt="Qrestor Logo" height="50" class="mr-0 lg:mr-2" /><span
            class="text-900 font-medium text-2xl line-height-3 mr-8"
            >QRestor</span
          >
        </a>
        <a
          class="cursor-pointer block lg:hidden text-700 p-ripple"
          v-ripple
          v-styleclass="{
            selector: '@next',
            enterClass: 'hidden',
            leaveToClass: 'hidden',
            hideOnOutsideClick: true
          }"
        >
          <i class="pi pi-bars text-4xl"></i>
        </a>
        <div
          class="align-items-center surface-0 flex-grow-1 justify-content-between hidden lg:flex absolute lg:static w-full left-0 px-6 lg:px-0 z-2"
          style="top: 120px"
        >
          <ul
            class="list-none p-0 m-0 flex lg:align-items-center select-none flex-column lg:flex-row cursor-pointer"
          >
            <li>
              <a
                @click="smoothScroll('#hero')"
                class="flex m-0 md:ml-5 px-0 py-3 text-900 font-medium line-height-3 p-ripple"
                v-ripple
              >
                <span>Home</span>
              </a>
            </li>
            <li>
              <a
                @click="smoothScroll('#features')"
                class="flex m-0 md:ml-5 px-0 py-3 text-900 font-medium line-height-3 p-ripple"
                v-ripple
              >
                <span>Features</span>
              </a>
            </li>
            <li>
              <a
                @click="smoothScroll('#highlights')"
                class="flex m-0 md:ml-5 px-0 py-3 text-900 font-medium line-height-3 p-ripple"
                v-ripple
              >
                <span>Highlights</span>
              </a>
            </li>
          </ul>
          <div
            class="flex justify-content-between lg:block border-top-1 lg:border-top-none surface-border py-3 lg:py-0 mt-3 lg:mt-0"
          >
            <Button
              class="p-button-text p-button-rounded border-none font-light line-height-2 text-blue-500"
              @click="$router.push('/auth/login')"
              >{{ loginLabel }}</Button
            >
            <Button
              v-if="!authStore.isAuthenticated()"
              label="Register"
              class="p-button-rounded border-none ml-5 font-light text-white line-height-2 bg-blue-500"
              @click="$router.push('/auth/register')"
            ></Button>
          </div>
        </div>
      </div>

      <div
        id="hero"
        class="flex flex-column pt-4 px-4 lg:px-8 overflow-hidden"
        style="
          background: linear-gradient(0deg, rgba(255, 255, 255, 0.2), rgba(255, 255, 255, 0.2)),
            radial-gradient(
              77.36% 256.97% at 77.36% 57.52%,
              rgb(238, 239, 175) 0%,
              rgb(195, 227, 250) 100%
            );
          clip-path: ellipse(150% 87% at 93% 13%);
        "
      >
        <div class="mx-4 md:mx-8 mt-0 md:mt-4">
          <h1 class="text-6xl font-bold text-gray-900 line-height-2">
            <span class="font-light block">QRestor platform</span>menu management solutution
          </h1>
          <p class="font-normal text-2xl line-height-3 md:mt-3 text-gray-700">
            Welcome to our QR Platform, where managing restaurant and catering menus is as easy as
            scan and serve! Say goodbye to printing costs and hello to seamless digital menus
            accessible via QR codes. Effortlessly create, customize, and update your menus with just
            a few clicks, ensuring your customers always have the latest offerings at their
            fingertips. Join us now and simplify the way you showcase your culinary creations!
          </p>
        </div>
        <div class="flex justify-content-center md:justify-content-end">
          <img src="/images/landing/screen-1.png" alt="Hero Image" class="w-4 md:w-5" />
        </div>
      </div>

      <div id="features" class="py-4 px-4 lg:px-8 mt-5 mx-0 lg:mx-8">
        <div class="grid justify-content-center">
          <div class="col-12 text-center mt-8 mb-4">
            <h2 class="text-900 font-normal mb-2">Marvelous Features</h2>
            <span class="text-600 text-2xl">QRrestor platform offers wide range of functionality, including:</span>
          </div>

          <div class="col-12 md:col-12 lg:col-4 p-0 lg:pr-5 lg:pb-5 mt-4 lg:mt-0">
            <div
              style="
                height: 160px;
                padding: 2px;
                border-radius: 10px;
                background: linear-gradient(
                    90deg,
                    rgba(253, 228, 165, 0.2),
                    rgba(187, 199, 205, 0.2)
                  ),
                  linear-gradient(180deg, rgba(253, 228, 165, 0.2), rgba(187, 199, 205, 0.2));
              "
            >
              <div class="p-3 surface-card h-full" style="border-radius: 8px">
                <div
                  class="flex align-items-center justify-content-center bg-yellow-200 mb-3"
                  style="width: 3.5rem; height: 3.5rem; border-radius: 10px"
                >
                  <i class="pi pi-fw pi-users text-2xl text-yellow-700"></i>
                </div>
                <h5 class="mb-2 text-900">Easy to Use</h5>
                <span class="text-600">Posuere morbi leo urna molestie.</span>
              </div>
            </div>
          </div>

          <div class="col-12 md:col-12 lg:col-4 p-0 lg:pr-5 lg:pb-5 mt-4 lg:mt-0">
            <div
              style="
                height: 160px;
                padding: 2px;
                border-radius: 10px;
                background: linear-gradient(
                    90deg,
                    rgba(145, 226, 237, 0.2),
                    rgba(251, 199, 145, 0.2)
                  ),
                  linear-gradient(180deg, rgba(253, 228, 165, 0.2), rgba(172, 180, 223, 0.2));
              "
            >
              <div class="p-3 surface-card h-full" style="border-radius: 8px">
                <div
                  class="flex align-items-center justify-content-center bg-cyan-200 mb-3"
                  style="width: 3.5rem; height: 3.5rem; border-radius: 10px"
                >
                  <i class="pi pi-fw pi-palette text-2xl text-cyan-700"></i>
                </div>
                <h5 class="mb-2 text-900">Fresh Design</h5>
                <span class="text-600">Semper risus in hendrerit.</span>
              </div>
            </div>
          </div>

          <div class="col-12 md:col-12 lg:col-4 p-0 lg:pb-5 mt-4 lg:mt-0">
            <div
              style="
                height: 160px;
                padding: 2px;
                border-radius: 10px;
                background: linear-gradient(
                    90deg,
                    rgba(145, 226, 237, 0.2),
                    rgba(172, 180, 223, 0.2)
                  ),
                  linear-gradient(180deg, rgba(172, 180, 223, 0.2), rgba(246, 158, 188, 0.2));
              "
            >
              <div class="p-3 surface-card h-full" style="border-radius: 8px">
                <div
                  class="flex align-items-center justify-content-center bg-indigo-200"
                  style="width: 3.5rem; height: 3.5rem; border-radius: 10px"
                >
                  <i class="pi pi-fw pi-map text-2xl text-indigo-700"></i>
                </div>
                <h5 class="mb-2 text-900">Well Documented</h5>
                <span class="text-600">Non arcu risus quis varius quam quisque.</span>
              </div>
            </div>
          </div>

          <div class="col-12 md:col-12 lg:col-4 p-0 lg:pr-5 lg:pb-5 mt-4 lg:mt-0">
            <div
              style="
                height: 160px;
                padding: 2px;
                border-radius: 10px;
                background: linear-gradient(
                    90deg,
                    rgba(187, 199, 205, 0.2),
                    rgba(251, 199, 145, 0.2)
                  ),
                  linear-gradient(180deg, rgba(253, 228, 165, 0.2), rgba(145, 210, 204, 0.2));
              "
            >
              <div class="p-3 surface-card h-full" style="border-radius: 8px">
                <div
                  class="flex align-items-center justify-content-center bg-bluegray-200 mb-3"
                  style="width: 3.5rem; height: 3.5rem; border-radius: 10px"
                >
                  <i class="pi pi-fw pi-id-card text-2xl text-bluegray-700"></i>
                </div>
                <h5 class="mb-2 text-900">Responsive Layout</h5>
                <span class="text-600">Nulla malesuada pellentesque elit.</span>
              </div>
            </div>
          </div>

          <div class="col-12 md:col-12 lg:col-4 p-0 lg:pr-5 lg:pb-5 mt-4 lg:mt-0">
            <div
              style="
                height: 160px;
                padding: 2px;
                border-radius: 10px;
                background: linear-gradient(
                    90deg,
                    rgba(187, 199, 205, 0.2),
                    rgba(246, 158, 188, 0.2)
                  ),
                  linear-gradient(180deg, rgba(145, 226, 237, 0.2), rgba(160, 210, 250, 0.2));
              "
            >
              <div class="p-3 surface-card h-full" style="border-radius: 8px">
                <div
                  class="flex align-items-center justify-content-center bg-orange-200 mb-3"
                  style="width: 3.5rem; height: 3.5rem; border-radius: 10px"
                >
                  <i class="pi pi-fw pi-star text-2xl text-orange-700"></i>
                </div>
                <h5 class="mb-2 text-900">Clean Code</h5>
                <span class="text-600">Condimentum lacinia quis vel eros.</span>
              </div>
            </div>
          </div>

          <div class="col-12 md:col-12 lg:col-4 p-0 lg:pb-5 mt-4 lg:mt-0">
            <div
              style="
                height: 160px;
                padding: 2px;
                border-radius: 10px;
                background: linear-gradient(
                    90deg,
                    rgba(251, 199, 145, 0.2),
                    rgba(246, 158, 188, 0.2)
                  ),
                  linear-gradient(180deg, rgba(172, 180, 223, 0.2), rgba(212, 162, 221, 0.2));
              "
            >
              <div class="p-3 surface-card h-full" style="border-radius: 8px">
                <div
                  class="flex align-items-center justify-content-center bg-pink-200 mb-3"
                  style="width: 3.5rem; height: 3.5rem; border-radius: 10px"
                >
                  <i class="pi pi-fw pi-moon text-2xl text-pink-700"></i>
                </div>
                <h5 class="mb-2 text-900">Dark Mode</h5>
                <span class="text-600">Convallis tellus id interdum velit laoreet.</span>
              </div>
            </div>
          </div>

          <div class="col-12 md:col-12 lg:col-4 p-0 lg:pr-5 mt-4 lg:mt-0">
            <div
              style="
                height: 160px;
                padding: 2px;
                border-radius: 10px;
                background: linear-gradient(
                    90deg,
                    rgba(145, 210, 204, 0.2),
                    rgba(160, 210, 250, 0.2)
                  ),
                  linear-gradient(180deg, rgba(187, 199, 205, 0.2), rgba(145, 210, 204, 0.2));
              "
            >
              <div class="p-3 surface-card h-full" style="border-radius: 8px">
                <div
                  class="flex align-items-center justify-content-center bg-teal-200 mb-3"
                  style="width: 3.5rem; height: 3.5rem; border-radius: 10px"
                >
                  <i class="pi pi-fw pi-shopping-cart text-2xl text-teal-700"></i>
                </div>
                <h5 class="mb-2 text-900">Ready to Use</h5>
                <span class="text-600">Mauris sit amet massa vitae.</span>
              </div>
            </div>
          </div>

          <div class="col-12 md:col-12 lg:col-4 p-0 lg:pr-5 mt-4 lg:mt-0">
            <div
              style="
                height: 160px;
                padding: 2px;
                border-radius: 10px;
                background: linear-gradient(
                    90deg,
                    rgba(145, 210, 204, 0.2),
                    rgba(212, 162, 221, 0.2)
                  ),
                  linear-gradient(180deg, rgba(251, 199, 145, 0.2), rgba(160, 210, 250, 0.2));
              "
            >
              <div class="p-3 surface-card h-full" style="border-radius: 8px">
                <div
                  class="flex align-items-center justify-content-center bg-blue-200 mb-3"
                  style="width: 3.5rem; height: 3.5rem; border-radius: 10px"
                >
                  <i class="pi pi-fw pi-globe text-2xl text-blue-700"></i>
                </div>
                <h5 class="mb-2 text-900">Modern Practices</h5>
                <span class="text-600">Elementum nibh tellus molestie nunc non.</span>
              </div>
            </div>
          </div>

          <div class="col-12 md:col-12 lg:col-4 p-0 lg-4 mt-4 lg:mt-0">
            <div
              style="
                height: 160px;
                padding: 2px;
                border-radius: 10px;
                background: linear-gradient(
                    90deg,
                    rgba(160, 210, 250, 0.2),
                    rgba(212, 162, 221, 0.2)
                  ),
                  linear-gradient(180deg, rgba(246, 158, 188, 0.2), rgba(212, 162, 221, 0.2));
              "
            >
              <div class="p-3 surface-card h-full" style="border-radius: 8px">
                <div
                  class="flex align-items-center justify-content-center bg-purple-200 mb-3"
                  style="width: 3.5rem; height: 3.5rem; border-radius: 10px"
                >
                  <i class="pi pi-fw pi-eye text-2xl text-purple-700"></i>
                </div>
                <h5 class="mb-2 text-900">Privacy</h5>
                <span class="text-600">Neque egestas congue quisque.</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div id="highlights" class="py-4 px-4 lg:px-8 mx-0 my-6 lg:mx-8">
        <div class="text-center">
          <h2 class="text-900 font-normal mb-2">Powerful Everywhere</h2>
          <span class="text-600 text-2xl">Amet consectetur adipiscing elit...</span>
        </div>

        <div class="grid mt-8 pb-2 md:pb-8">
          <div
            class="flex justify-content-center col-12 lg:col-6 bg-purple-100 p-0 flex-order-1 lg:flex-order-0"
            style="border-radius: 8px"
          >
            <img src="/images/landing/menu-mobile.jpg" class="w-11" alt="mockup mobile" />
          </div>

          <div
            class="col-12 lg:col-6 my-auto flex flex-column lg:align-items-end text-center lg:text-right"
          >
            <div
              class="flex align-items-center justify-content-center bg-purple-200 align-self-center lg:align-self-end"
              style="width: 4.2rem; height: 4.2rem; border-radius: 10px"
            >
              <i class="pi pi-fw pi-mobile text-5xl text-purple-700"></i>
            </div>
            <h2 class="line-height-1 text-900 text-4xl font-normal">Minimalistic and functional Menu</h2>
            <span class="text-700 text-2xl line-height-3 ml-0 md:ml-2" style="max-width: 650px"
              >
              Adipiscing commodo elit at imperdiet dui. Viverra nibh cras pulvinar mattis nunc <sed></sed>
            </span
            >
          </div>
        </div>

        <div class="grid my-8 pt-2 md:pt-8">
          <div
            class="col-12 lg:col-6 my-auto flex flex-column text-center lg:text-left lg:align-items-start"
          >
            <div
              class="flex align-items-center justify-content-center bg-yellow-200 align-self-center lg:align-self-start"
              style="width: 4.2rem; height: 4.2rem; border-radius: 10px"
            >
              <i class="pi pi-fw pi-desktop text-5xl text-yellow-700"></i>
            </div>
            <h2 class="line-height-1 text-900 text-4xl font-normal">Celerisque Eu Ultrices</h2>
            <span class="text-700 text-2xl line-height-3 mr-0 md:mr-2" style="max-width: 650px"
              >Adipiscing commodo elit at imperdiet dui. Viverra nibh cras pulvinar mattis nunc sed
              blandit libero. Suspendisse in est ante in. Mauris pharetra et ultrices neque ornare
              aenean euismod elementum nisi.</span
            >
          </div>

          <div
            class="flex justify-content-end flex-order-1 sm:flex-order-2 col-12 lg:col-6 bg-yellow-100 p-0"
            style="border-radius: 8px"
          >
            <img src="/images/landing/mockup-desktop.svg" class="w-11" alt="mockup" />
          </div>
        </div>
      </div>

      <div id="pricing" class="py-4 px-4 lg:px-8 my-2 md:my-4">
        <div class="text-center">
          <h2 class="text-900 font-normal mb-2">Matchless Pricing</h2>
          <span class="text-600 text-2xl">Amet consectetur adipiscing elit...</span>
        </div>

        <div class="grid justify-content-between mt-8 md:mt-0">
          <div class="col-12 lg:col-4 p-0 md:p-3">
            <div
              class="p-3 flex flex-column border-200 pricing-card cursor-pointer border-2 hover:border-primary transition-duration-300 transition-all"
              style="border-radius: 10px"
            >
              <h3 class="text-900 text-center my-5">Free</h3>
              <img src="/images/landing/free.svg" class="w-10 h-10 mx-auto" alt="free" />
              <div class="my-5 text-center">
                <span class="text-5xl font-bold mr-2 text-900">$0</span>
                <span class="text-600">per month</span>
                <Button
                  label="Get Started"
                  class="block mx-auto mt-4 p-button-rounded border-none ml-3 font-light line-height-2 bg-blue-500 text-white"
                ></Button>
              </div>
              <Divider class="w-full bg-surface-200"></Divider>
              <ul class="my-5 list-none p-0 flex text-900 flex-column">
                <li class="py-2">
                  <i class="pi pi-fw pi-check text-xl text-cyan-500 mr-2"></i>
                  <span class="text-xl line-height-3">Responsive Layout</span>
                </li>
                <li class="py-2">
                  <i class="pi pi-fw pi-check text-xl text-cyan-500 mr-2"></i>
                  <span class="text-xl line-height-3">Unlimited Push Messages</span>
                </li>
                <li class="py-2">
                  <i class="pi pi-fw pi-check text-xl text-cyan-500 mr-2"></i>
                  <span class="text-xl line-height-3">50 Support Ticket</span>
                </li>
                <li class="py-2">
                  <i class="pi pi-fw pi-check text-xl text-cyan-500 mr-2"></i>
                  <span class="text-xl line-height-3">Free Shipping</span>
                </li>
              </ul>
            </div>
          </div>

          <div class="col-12 lg:col-4 p-0 md:p-3 mt-4 md:mt-0">
            <div
              class="p-3 flex flex-column border-200 pricing-card cursor-pointer border-2 hover:border-primary transition-duration-300 transition-all"
              style="border-radius: 10px"
            >
              <h3 class="text-900 text-center my-5">Startup</h3>
              <img src="/images/landing/startup.svg" class="w-10 h-10 mx-auto" alt="startup" />
              <div class="my-5 text-center">
                <span class="text-5xl font-bold mr-2 text-900">$1</span>
                <span class="text-600">per month</span>
                <Button
                  label="Try Free"
                  class="block mx-auto mt-4 p-button-rounded border-none ml-3 font-light line-height-2 bg-blue-500 text-white"
                ></Button>
              </div>
              <Divider class="w-full bg-surface-200"></Divider>
              <ul class="my-5 list-none p-0 flex text-900 flex-column">
                <li class="py-2">
                  <i class="pi pi-fw pi-check text-xl text-cyan-500 mr-2"></i>
                  <span class="text-xl line-height-3">Responsive Layout</span>
                </li>
                <li class="py-2">
                  <i class="pi pi-fw pi-check text-xl text-cyan-500 mr-2"></i>
                  <span class="text-xl line-height-3">Unlimited Push Messages</span>
                </li>
                <li class="py-2">
                  <i class="pi pi-fw pi-check text-xl text-cyan-500 mr-2"></i>
                  <span class="text-xl line-height-3">50 Support Ticket</span>
                </li>
                <li class="py-2">
                  <i class="pi pi-fw pi-check text-xl text-cyan-500 mr-2"></i>
                  <span class="text-xl line-height-3">Free Shipping</span>
                </li>
              </ul>
            </div>
          </div>

          <div class="col-12 lg:col-4 p-0 md:p-3 mt-4 md:mt-0">
            <div
              class="p-3 flex flex-column border-200 pricing-card cursor-pointer border-2 hover:border-primary transition-duration-300 transition-all"
              style="border-radius: 10px"
            >
              <h3 class="text-900 text-center my-5">Enterprise</h3>
              <img
                src="/images/landing/enterprise.svg"
                class="w-10 h-10 mx-auto"
                alt="enterprise"
              />
              <div class="my-5 text-center">
                <span class="text-5xl font-bold mr-2 text-900">$999</span>
                <span class="text-600">per month</span>
                <Button
                  label="Get a Quote"
                  class="block mx-auto mt-4 p-button-rounded border-none ml-3 font-light line-height-2 bg-blue-500 text-white"
                ></Button>
              </div>
              <Divider class="w-full bg-surface-200"></Divider>
              <ul class="my-5 list-none p-0 flex text-900 flex-column">
                <li class="py-2">
                  <i class="pi pi-fw pi-check text-xl text-cyan-500 mr-2"></i>
                  <span class="text-xl line-height-3">Responsive Layout</span>
                </li>
                <li class="py-2">
                  <i class="pi pi-fw pi-check text-xl text-cyan-500 mr-2"></i>
                  <span class="text-xl line-height-3">Unlimited Push Messages</span>
                </li>
                <li class="py-2">
                  <i class="pi pi-fw pi-check text-xl text-cyan-500 mr-2"></i>
                  <span class="text-xl line-height-3">50 Support Ticket</span>
                </li>
                <li class="py-2">
                  <i class="pi pi-fw pi-check text-xl text-cyan-500 mr-2"></i>
                  <span class="text-xl line-height-3">Free Shipping</span>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>

      <div class="py-4 px-4 mx-0 mt-8 lg:mx-8">
        <div class="grid justify-content-between">
          <div class="col-12 md:col-2" style="margin-top: -1.5rem">
            <a
              @click="smoothScroll('#home')"
              class="flex flex-wrap align-items-center justify-content-center md:justify-content-start md:mb-0 mb-3 cursor-pointer"
            >
              <img :src="logoUrl" alt="footer sections" width="50" height="50" class="mr-2" />
              <h4 class="font-medium text-3xl text-900">QRestor</h4>
            </a>
          </div>

          <div class="col-12 md:col-10 lg:col-7">
            <div class="grid text-center md:text-left">
              <div class="col-12 md:col-3">
                <h4 class="font-medium text-2xl line-height-3 mb-3 text-900">Company</h4>
                <a class="line-height-3 text-xl block cursor-pointer mb-2 text-700">About Us</a>
                <a class="line-height-3 text-xl block cursor-pointer mb-2 text-700">News</a>
                <a class="line-height-3 text-xl block cursor-pointer mb-2 text-700"
                  >Investor Relations</a
                >
                <a class="line-height-3 text-xl block cursor-pointer mb-2 text-700">Careers</a>
                <a class="line-height-3 text-xl block cursor-pointer text-700">Media Kit</a>
              </div>

              <div class="col-12 md:col-3 mt-4 md:mt-0">
                <h4 class="font-medium text-2xl line-height-3 mb-3 text-900">Resources</h4>
                <a class="line-height-3 text-xl block cursor-pointer mb-2 text-700">Get Started</a>
                <a class="line-height-3 text-xl block cursor-pointer mb-2 text-700">Learn</a>
                <a class="line-height-3 text-xl block cursor-pointer text-700">Case Studies</a>
              </div>

              <div class="col-12 md:col-3 mt-4 md:mt-0">
                <h4 class="font-medium text-2xl line-height-3 mb-3 text-900">Community</h4>
                <a class="line-height-3 text-xl block cursor-pointer mb-2 text-700">Discord</a>
                <a class="line-height-3 text-xl block cursor-pointer mb-2 text-700"
                  >Events<img src="/images/landing/new-badge.svg" class="ml-2"
                /></a>
                <a class="line-height-3 text-xl block cursor-pointer mb-2 text-700">FAQ</a>
                <a class="line-height-3 text-xl block cursor-pointer text-700">Blog</a>
              </div>

              <div class="col-12 md:col-3 mt-4 md:mt-0">
                <h4 class="font-medium text-2xl line-height-3 mb-3 text-900">Legal</h4>
                <a class="line-height-3 text-xl block cursor-pointer mb-2 text-700">Brand Policy</a>
                <a class="line-height-3 text-xl block cursor-pointer mb-2 text-700"
                  >Privacy Policy</a
                >
                <a class="line-height-3 text-xl block cursor-pointer text-700">Terms of Service</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <AppConfig simple />
</template>
<!-- <style scoped>
#hero {
    background: linear-gradient(0deg, rgba(255, 255, 255, 0.2), rgba(255, 255, 255, 0.2)), radial-gradient(77.36% 256.97% at 77.36% 57.52%, #eeefaf 0%, #c3e3fa 100%);
    height: 700px;
    overflow: hidden;
}

@media screen and (min-width: 768px) {
    #hero {
        -webkit-clip-path: ellipse(150% 87% at 93% 13%);
        clip-path: ellipse(150% 87% at 93% 13%);
        height: 530px;
    }
}

@media screen and (min-width: 1300px) {
    #hero > img {
        position: absolute;
    }

    #hero > div > p {
        max-width: 450px;
    }
}

@media screen and (max-width: 1300px) {
    #hero {
        height: 600px;
    }

    #hero > img {
        position: static;
        transform: scale(1);
        margin-left: auto;
    }

    #hero > div {
        width: 100%;
    }

    #hero > div > p {
        width: 100%;
        max-width: 100%;
    }
}
</style> -->
