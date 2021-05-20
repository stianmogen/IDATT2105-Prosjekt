import URLS from 'URLS';
import { Link } from 'react-router-dom';
import { useIsAuthenticated } from 'hooks/User';

// Material UI Components
import { makeStyles, useTheme } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import Drawer from '@material-ui/core/Drawer';

const useStyles = makeStyles((theme) => ({
  sidebar: {
    backgroundColor: theme.palette.secondary.main,
    width: '100vw',
    overflow: 'auto',
    height: 'calc(100% - 64px)',
    marginTop: 64,
    [theme.breakpoints.down('xs')]: {
      height: 'calc(100% - 56px)',
      marginTop: 56,
    },
  },
  root: {
    padding: theme.spacing(2, 3),
    display: 'flex',
    flexDirection: 'column',
  },
  text: {
    color: theme.palette.getContrastText(theme.palette.secondary.main),
    padding: theme.spacing(1),
    textDecoration: 'none',
    width: 'fit-content',
  },
}));

type SidebarItemProps = {
  text: string;
  to: string;
};

const SidebarItem = ({ text, to }: SidebarItemProps) => {
  const classes = useStyles();
  return (
    <Typography
      className={classes.text}
      component={Link}
      onClick={to === window.location.pathname ? () => window.location.reload() : undefined}
      to={to}
      variant='h2'>
      {text}
    </Typography>
  );
};

export type IProps = {
  items: Array<SidebarItemProps>;
  onClose: () => void;
  open: boolean;
};

const Sidebar = ({ items, onClose, open }: IProps) => {
  const classes = useStyles();
  const isAuthenticated = useIsAuthenticated();
  const theme = useTheme();
  return (
    <Drawer anchor='top' classes={{ paper: classes.sidebar }} onClose={onClose} open={open} style={{ zIndex: theme.zIndex.drawer - 1 }}>
      <div className={classes.root}>
        {items.map((item, i) => (
          <SidebarItem key={i} {...item} />
        ))}
        {isAuthenticated ? <SidebarItem text='My Bookings' to={URLS.BOOKINGS} /> : <SidebarItem text='Log in' to={URLS.LOGIN} />}
      </div>
    </Drawer>
  );
};

export default Sidebar;
