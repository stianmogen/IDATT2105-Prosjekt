import { createMuiTheme } from '@material-ui/core/styles';

// Icons
import DarkIcon from '@material-ui/icons/Brightness2Outlined';
import AutomaticIcon from '@material-ui/icons/DevicesOutlined';
import LightIcon from '@material-ui/icons/WbSunnyOutlined';

declare module '@material-ui/core/styles/createPalette' {
  interface Palette {
    borderWidth: string;
    colors: {
      footer: string;
    };
  }

  interface PaletteOptions {
    borderWidth: string;
    colors: {
      footer: string;
    };
  }
}

export const themesDetails = [
  { key: 'light', name: 'Lyst', icon: LightIcon },
  { key: 'automatic', name: 'Automatisk', icon: AutomaticIcon },
  { key: 'dark', name: 'Mørkt', icon: DarkIcon },
] as const;
export const themes = themesDetails.map((theme) => theme.key);
export type ThemeTypes = typeof themes[number];

export const getTheme = (theme: ThemeTypes, prefersDarkMode: boolean) => {
  // eslint-disable-next-line comma-spacing
  const get = <T,>({ light, dark }: { light: T; dark: T }): T => {
    switch (theme) {
      case 'automatic':
        return prefersDarkMode ? dark : light;
      case 'dark':
        return dark;
      default:
        return light;
    }
  };

  return createMuiTheme({
    breakpoints: {
      values: {
        xs: 0,
        sm: 400,
        md: 600,
        lg: 900,
        xl: 1200,
      },
    },
    components: {
      MuiAvatar: {
        styleOverrides: {
          root: {
            backgroundColor: '#f57c00',
            color: 'white',
            fontWeight: 'bold',
          },
        },
      },
      MuiCssBaseline: {
        styleOverrides: {
          '@global': {
            html: {
              WebkitFontSmoothing: 'auto',
            },
            a: {
              color: get<string>({ light: '#1D448C', dark: '#9ec0ff' }),
            },
          },
        },
      },
    },
    palette: {
      mode: get<'light' | 'dark'>({ light: 'light', dark: 'dark' }),
      primary: {
        main: get<string>({ light: '#4056a1', dark: '#90caf9' }),
      },
      secondary: {
        main: get<string>({ light: '#f13c20', dark: '#f48fb1' }),
      },
      error: {
        main: get<string>({ light: '#b20101', dark: '#ff6060' }),
      },
      text: {
        secondary: get<string>({ light: '#333333', dark: '#cccccc' }),
      },
      borderWidth: '1px',
      background: {
        default: get<string>({ light: '#efe2ba', dark: '#121212' }),
        paper: get<string>({ light: '#c5cbe3', dark: '#232323' }),
      },
      colors: {
        footer: '#ffb74d',
      },
    },
    shape: {
      borderRadius: 10,
    },
    typography: {
      h1: {
        fontSize: '3.1rem',
        fontFamily: 'Oswald, Roboto, sans-serif',
        fontWeight: 900,
      },
      h2: {
        fontSize: '2.2rem',
        fontFamily: 'Oswald, Roboto, sans-serif',
        fontWeight: 700,
      },
      h3: {
        fontSize: '1.5rem',
      },
    },
  });
};
