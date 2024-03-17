import PLACEHOLDER_IMAGE_URL from '@/qr/assets/placeholder_image.png'
import type { StyledQRCodeProps } from '@/qr/components/StyledQRCode.vue'
import type { DrawType } from 'qr-code-styling'
import GeeksHackingConfig from '@/qr/assets/presets/1.json'
import SpDigitalConfig from '@/qr/assets/presets/3.json'
import GovtechStackCommunityConfig from '@/qr/assets/presets/2.json'

export interface CustomStyleProps {
  borderRadius?: string
  background?: string
}

export type PresetAttributes = {
  style: CustomStyleProps
  name: string
}

export type Preset = Required<StyledQRCodeProps> & PresetAttributes

const defaultPresetOptions = {
  backgroundOptions: {
    color: 'transparent'
  },
  imageOptions: {
    margin: 0
  },
  width: 200,
  height: 200,
  margin: 0,
  type: 'svg' as DrawType
}

// Company / Tech presets

export const padletPreset: Preset = {
  ...defaultPresetOptions,
  name: '1',
  data: 'https://www.qrestor.com',
  image: '/favicon.svg',
  width: 200,
  height: 200,
  margin: 0,
  dotsOptions: { color: '#7ABE4A', type: 'extra-rounded' },
  cornersSquareOptions: { color: '#ed457e', type: 'extra-rounded' },
  cornersDotOptions: { color: '#ed457e', type: 'square' },
  style: { borderRadius: '24px', background: '#000000' }
}

export const vercelLightPreset: Preset = {
  ...defaultPresetOptions,
  name: '2',
  data: 'https://www.qrestor.com',
  image: '/favicon.svg',
  dotsOptions: { color: '#000000', type: 'classy' },
  cornersSquareOptions: { color: '#000000', type: 'square' },
  cornersDotOptions: { color: '#000000', type: 'square' },
  style: { borderRadius: '0px', background: '#FFFFFF' },
  margin: 8,
  imageOptions: {
    margin: 8
  }
}

export const vercelDarkPreset: Preset = {
  ...defaultPresetOptions,
  name: '3',
  data: 'https://www.qrestor.com',
  image: '/favicon.svg',
  dotsOptions: { color: '#FFFFFF', type: 'classy' },
  cornersSquareOptions: { color: '#FFFFFF', type: 'square' },
  cornersDotOptions: { color: '#FFFFFF', type: 'square' },
  style: { borderRadius: '0px', background: '#000000' },
  margin: 8,
  imageOptions: {
    margin: 8
  }
}

export const supabaseGreenPreset: Preset = {
  ...defaultPresetOptions,
  name: '4',
  data: 'https://www.qrestor.com',
  image: '/favicon.svg',
  dotsOptions: {
    color: '#3ecf8e',
    type: 'classy-rounded'
  },
  cornersSquareOptions: {
    color: '#3ecf8e',
    type: 'square'
  },
  cornersDotOptions: {
    color: '#3ecf8e',
    type: 'square'
  },
  imageOptions: {
    margin: 8
  },
  style: {
    borderRadius: '12px',
    background: '#000000'
  }
}

export const supabasePurplePreset: Preset = {
  ...defaultPresetOptions,
  name: '5',
  data: 'https://www.qrestor.com',
  image: '/favicon.svg',
  dotsOptions: {
    color: '#7700ff',
    type: 'classy-rounded'
  },
  cornersSquareOptions: {
    color: '#7700ff',
    type: 'square'
  },
  cornersDotOptions: {
    color: '#7700ff',
    type: 'square'
  },
  imageOptions: {
    margin: 0
  },
  style: {
    borderRadius: '12px',
    background: '#000000'
  }
}

export const uiliciousPreset: Preset = {
  ...defaultPresetOptions,
  name: '6',
  data: 'https://www.qrestor.com',
  image: '/favicon.svg',
  dotsOptions: {
    color: '#0f8b4c',
    type: 'extra-rounded'
  },
  cornersSquareOptions: {
    color: '#0f8b4c',
    type: 'extra-rounded'
  },
  cornersDotOptions: {
    color: '#111111',
    type: 'square'
  },
  imageOptions: {
    margin: 8
  },
  style: {
    borderRadius: '24px',
    background: '#ffffff'
  }
}

export const viteConf2023Preset: Preset = {
  ...defaultPresetOptions,
  name: '7',
  data: 'https://www.qrestor.com',
  image: '/favicon.svg',
  margin: 12,
  dotsOptions: {
    color: '#a661ea',
    type: 'classy-rounded'
  },
  cornersSquareOptions: {
    color: '#5d69a0',
    type: 'square'
  },
  cornersDotOptions: {
    color: '#5da5d8',
    type: 'square'
  },
  imageOptions: {
    margin: 0
  },
  style: {
    borderRadius: '12px',
    background: '#060817'
  }
}

export const vueJsPreset: Preset = {
  ...defaultPresetOptions,
  name: '8',
  data: 'https://www.qrestor.com',
  image: '/favicon.svg',
  dotsOptions: {
    color: '#35495e',
    type: 'rounded'
  },
  cornersSquareOptions: {
    color: '#40b883',
    type: 'extra-rounded'
  },
  cornersDotOptions: {
    color: '#40b883',
    type: 'dot'
  },
  imageOptions: {
    margin: 2
  },
  style: {
    borderRadius: '24px',
    background: '#ffffff'
  }
}

// Individual presets

export const defaultPreset: Preset = {
  ...defaultPresetOptions,
  name: 'Default',
  data: 'https://www.qrestor.com',
  image: PLACEHOLDER_IMAGE_URL,
  dotsOptions: {
    color: '#abcbca',
    type: 'extra-rounded'
  },
  cornersSquareOptions: {
    color: '#abcbca',
    type: 'extra-rounded'
  },
  cornersDotOptions: {
    color: '#abcbca',
    type: 'square'
  },
  style: {
    borderRadius: '24px',
    background: '#697d80'
  }
}
export const pejuangKodePreset: Preset = {
  ...defaultPresetOptions,
  name: '9',
  data: 'https://www.qrestor.com',
  image: '/favicon.svg',
  width: 400,
  height: 400,
  margin: 0,
  dotsOptions: { color: '#252f3f', type: 'classy-rounded' },
  cornersSquareOptions: { color: '#252f3f', type: 'dot' },
  cornersDotOptions: { color: '#f05252', type: 'dot' },
  imageOptions: { margin: -1 },
  style: { borderRadius: '22px', background: '#ffffff' }
}

export const geeksHackingPreset = {
  ...defaultPresetOptions,
  name: '10',
  ...GeeksHackingConfig.props,
  style: GeeksHackingConfig.style
} as Preset

export const spDigitalPreset = {
  ...defaultPresetOptions,
  name: '12',
  ...SpDigitalConfig.props,
  style: SpDigitalConfig.style
} as Preset

export const govtechStackCommunityPreset = {
  ...defaultPresetOptions,
  name: '13',
  ...GovtechStackCommunityConfig.props,
  style: GovtechStackCommunityConfig.style
} as Preset

export const allPresets: Preset[] = [
  defaultPreset,
  ...[
    padletPreset,
    uiliciousPreset,
    supabaseGreenPreset,
    supabasePurplePreset,
    vercelLightPreset,
    vercelDarkPreset,
    viteConf2023Preset,
    vueJsPreset,
    pejuangKodePreset,
    geeksHackingPreset,
    spDigitalPreset,
    govtechStackCommunityPreset
  ].sort((a, b) => a.name.localeCompare(b.name))
]
