import { useMemo, useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import URLS from 'URLS';
import classNames from 'classnames';
import { useUser, useIsAuthenticated } from 'hooks/User';

// Material UI Components
import { makeStyles } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Hidden from '@material-ui/core/Hidden';
import Button from '@material-ui/core/Button';
import IconButton from '@material-ui/core/IconButton';

// Assets/Icons
import MenuIcon from '@material-ui/icons/MenuRounded';
import CloseIcon from '@material-ui/icons/CloseRounded';
import PersonOutlineIcon from '@material-ui/icons/PersonRounded';

// Project Components
import Sidebar from 'components/navigation/Sidebar';
import Logo from 'components/miscellaneous/Logo';

const useStyles = makeStyles((theme) => ({
  appBar: {
    boxSizing: 'border-box',
    backgroundColor: theme.palette.secondary.main,
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
    gridTemplateColumns: '120px 1fr 120px',
    [theme.breakpoints.down('xl')]: {
      gridTemplateColumns: '100px 1fr 100px',
    },
    [theme.breakpoints.down('lg')]: {
      gridTemplateColumns: '80px 1fr',
    },
  },
  logo: {
    height: 45,
    width: 'auto',
    marginLeft: 0,
  },
  items: {
    display: 'flex',
    justifyContent: 'flex-start',
    color: theme.palette.common.white,
  },
  right: {
    display: 'flex',
    justifyContent: 'flex-end',
  },
  menuButton: {
    color: theme.palette.getContrastText(theme.palette.secondary.main),
    margin: 'auto 0',
  },
  selected: {
    borderBottom: '2px solid ' + theme.palette.getContrastText(theme.palette.secondary.main),
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
    alignSelf: 'center',
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
    <div className={classNames(classes.topbarItem, selected && classes.selected)}>
      <Button color='inherit' component={Link} onClick={selected ? () => window.location.reload() : undefined} to={to}>
        {text}
      </Button>
    </div>
  );
};

const Topbar = () => {
  const isAuthenticated = useIsAuthenticated();
  const { data: user } = useUser();
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const classes = useStyles();

  useEffect(() => {
    window.scrollTo(0, 0);
  }, []);

  const items = useMemo(
    () =>
      [
        { text: 'About', to: URLS.LANDING },
        { text: 'Book Room', to: URLS.ACTIVITIES },
        isAuthenticated ? { text: 'Logged In', to: URLS.LOGIN } : { text: 'Not logged in', to: URLS.LANDING },
      ] as Array<TopBarItemProps>,
    [isAuthenticated],
  );

  return (
    <AppBar className={classes.appBar} color='primary' elevation={0} position='fixed'>
      <Toolbar disableGutters>
        <div className={classes.toolbar}>
          <Link to={URLS.LANDING}>
            <Logo className={classes.logo} darkColor='white' lightColor={!sidebarOpen ? 'blue' : 'white'} size='large' />
          </Link>
          <Hidden mdDown>
            <div className={classes.items}>
              {items.map((item, i) => (
                <TopBarItem key={i} {...item} />
              ))}
            </div>
          </Hidden>
          <div className={classes.right}>
            {user ? (
              <Button component={Link} onClick={URLS.LOGIN === location.pathname ? () => location.reload() : undefined} to={URLS.LOGIN}>
                <Hidden smDown>
                  <Typography className={classes.profileName}>{user.first_name}</Typography>
                </Hidden>
                {user.first_name.substr(0, 1)}
              </Button>
            ) : (
              <Hidden mdDown>
                <IconButton className={classes.menuButton} component={Link} to={URLS.LOGIN}>
                  <PersonOutlineIcon />
                </IconButton>
              </Hidden>
            )}
            <Hidden lgUp>
              <IconButton className={classes.menuButton} onClick={() => setSidebarOpen((prev) => !prev)}>
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
