import { defineConfig } from 'vite'
import envCompatible from 'vite-plugin-env-compatible'
import react from '@vitejs/plugin-react'

// https://vite.dev/config/
export default defineConfig({
  envPrefix: "REACT_APP_",
  plugins: [react(), envCompatible],
  server: {
    host: true,  
    port: 3000,
  },
})
