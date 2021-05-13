import { ReactNode, useEffect, lazy, Suspense } from 'react';
import ReactDOM from 'react-dom';
import 'assets/css/index.css';
import { StylesProvider } from '@material-ui/core';
import CssBaseline from '@material-ui/core/CssBaseline';
import { BrowserRouter, Routes, Route, useLocation } from 'react-router-dom';
import { QueryClient, QueryClientProvider } from 'react-query';
import URLS from 'URLS';
import 'delayed-scroll-restoration-polyfill';

// Services
import { ThemeProvider } from 'hooks/ThemeContext';
import { SnackbarProvider } from 'hooks/Snackbar';

// Project components
import Navigation from 'components/navigation/Navigation';

// Project containers
import Landing from 'containers/Landing';
const Http404 = lazy(() => import('containers/Http404'));
const LogIn = lazy(() => import('containers/LogIn'));
const Rooms = lazy(() => import('containers/Rooms'));

type ProvidersProps = {
  children: ReactNode;
};

export const Providers = ({ children }: ProvidersProps) => {
  const queryClient = new QueryClient({
    defaultOptions: {
      queries: {
        staleTime: 1000 * 60 * 2, // Don't refetch data before 2 min has passed
        refetchOnWindowFocus: false,
      },
    },
  });
  return (
    <QueryClientProvider client={queryClient}>
      <StylesProvider injectFirst>
        <ThemeProvider>
          <CssBaseline />
          <SnackbarProvider>{children}</SnackbarProvider>
        </ThemeProvider>
      </StylesProvider>
    </QueryClientProvider>
  );
};

const AppRoutes = () => {
  const location = useLocation();
  useEffect(() => {
    window.gtag('event', 'page_view', {
      page_location: window.location.href,
      page_path: window.location.pathname,
    });
  }, [location]);
  return (
    <Routes>
      <Route element={<Landing />} path={URLS.LANDING} />
      <Route path={URLS.ROOMS}>
        <Route element={<Rooms />} path='' />
      </Route>
      <Route element={<LogIn />} path={URLS.LOGIN} />

      <Route element={<Http404 />} path='*' />
    </Routes>
  );
};

export const Application = () => {
  return (
    <Providers>
      <BrowserRouter>
        <Suspense fallback={<Navigation isLoading />}>
          <AppRoutes />
        </Suspense>
      </BrowserRouter>
    </Providers>
  );
};

ReactDOM.render(<Application />, document.getElementById('root'));
