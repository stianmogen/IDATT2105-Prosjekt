import { useMemo, useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import URLS from 'URLS';
import { useUser, useIsAuthenticated, useLogout } from 'hooks/User';

// Material UI Components
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Hidden from '@material-ui/core/Hidden';
import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';

// Assets/Icons
import MenuIcon from '@material-ui/icons/MenuRounded';
import CloseIcon from '@material-ui/icons/CloseRounded';

// Project Components
import Sidebar from 'components/navigation/Sidebar';
import Logo from 'components/miscellaneous/Logo';
import ThemeSettings from 'components/miscellaneous/ThemeSettings';

const useStyles = makeStyles((theme) => ({
  appBar: {
    boxSizing: 'border-box',
    backgroundColor: theme.palette.colors.topbar,
    color: theme.palette.text.primary,
    flexGrow: 1,
    zIndex: theme.zIndex.drawer + 1,
    transition: '0.4s',
  },
  toolbar: {
    width: '100%',
    maxWidth: theme.breakpoints.values.xl,
    margin: 'auto',
    padding: theme.spacing(0, 1),
    display: 'grid',
    gridTemplateColumns: '120px 1fr auto',
    [theme.breakpoints.down('md')]: {
      gridTemplateColumns: '80px 1fr',
    },
  },
  logo: {
    height: 45,
    width: 'auto',
    marginLeft: 0,
  },
  items: {
    display: 'grid',
    gap: theme.spacing(1),
    alignItems: 'self-start',
    gridAutoFlow: 'column',
    color: theme.palette.common.white,
    width: 'fit-content',
  },
  right: {
    display: 'grid',
    gap: theme.spacing(1),
    gridTemplateColumns: '35px auto',
    [theme.breakpoints.down('md')]: {
      display: 'grid',
      justifyContent: 'flex-end',
    },
  },
  profileName: {
    margin: `auto ${theme.spacing(1)}px`,
    color: theme.palette.common.white,
    textAlign: 'right',
    overflow: 'hidden',
    textOverflow: 'ellipsis',
    whiteSpace: 'nowrap',
  },
  topbarItem: {
    height: 35,
    margin: 'auto 0',
    color: theme.palette.common.white,
  },
}));

export type TopBarItemProps = {
  text: string;
  to: string;
};

const TopBarItem = ({ text, to }: TopBarItemProps) => {
  const classes = useStyles({});
  const selected = useMemo(() => location.pathname === to, [location.pathname, to]);
  return (
    <Button
      className={classes.topbarItem}
      color='inherit'
      component={Link}
      onClick={selected ? () => window.location.reload() : undefined}
      to={to}
      variant={selected ? 'outlined' : 'text'}>
      {text}
    </Button>
  );
};

const Topbar = () => {
  const isAuthenticated = useIsAuthenticated();
  const { data: user } = useUser();
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const classes = useStyles();
  const logout = useLogout();

  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  const items = useMemo(
    () =>
      [
        { text: 'Book Room', to: URLS.ROOMS },
        { text: 'About', to: URLS.ABOUT },
        ...(isAuthenticated ? [{ text: 'Min profil', to: URLS.LOGIN }] : []),
      ] as Array<TopBarItemProps>,
    [isAuthenticated],
  );

  return (
    <AppBar className={classes.appBar} color='primary' elevation={0} position='fixed'>
      <Toolbar disableGutters>
        <div className={classes.toolbar}>
          <Link to={URLS.LANDING}>
            <Logo className={classes.logo} size='large' />
          </Link>
          <Hidden mdDown>
            <div className={classes.items}>
              {items.map((item, i) => (
                <TopBarItem key={i} {...item} />
              ))}
            </div>
          </Hidden>
          <div className={classes.right}>
            <Hidden mdDown>
              <ThemeSettings className={classes.topbarItem} />
              {user ? (
                <Button className={classes.topbarItem} color='inherit' onClick={logout} variant='outlined'>
                  Logg ut
                </Button>
              ) : (
                <Button className={classes.topbarItem} color='inherit' component={Link} to={URLS.LOGIN} variant='outlined'>
                  Logg inn
                </Button>
              )}
            </Hidden>
            <Hidden mdUp>
              <IconButton className={classes.topbarItem} onClick={() => setSidebarOpen((prev) => !prev)}>
                {sidebarOpen ? <CloseIcon /> : <MenuIcon />}
              </IconButton>
              <Sidebar items={items} onClose={() => setSidebarOpen(false)} open={sidebarOpen} />
            </Hidden>
          </div>
        </div>
      </Toolbar>
    </AppBar>
  );
};

export default Topbar;
