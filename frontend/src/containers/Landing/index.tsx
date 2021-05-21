import Helmet from 'react-helmet';
import { Link } from 'react-router-dom';
import URLS from 'URLS';

// Material UI Components
import { makeStyles } from '@material-ui/core/styles';
import { Typography, Button } from '@material-ui/core';

// Project Components
import Navigation from 'components/navigation/Navigation';
import image from 'assets/img/DefaultBackground.jpg';
import { useIsAuthenticated } from 'hooks/User';

const useStyles = makeStyles((theme) => ({
  header: {
    marginTop: theme.spacing(1),
  },
  cover: {
    position: 'relative',
    color: theme.palette.common.white,
    height: '100vh',
    width: '100%',
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    alignItems: 'center',
  },
  img: {
    background: `${theme.palette.colors.gradient}, url(${image}) center center/cover no-repeat scroll`,
    objectFit: 'contain',
    boxShadow: 'inset 0 0 0 1000px rgba(0, 0, 0, 0.2)',
    position: 'absolute',
    top: 0,
    bottom: 0,
    left: 0,
    right: 0,
    filter: 'blur(1px)',
    zIndex: -1,
  },
  btnGroup: {
    display: 'grid',
    gap: theme.spacing(1),
    paddingTop: theme.spacing(1),
    gridTemplateColumns: 'auto auto',
  },
  button: {
    color: theme.palette.common.white,
    borderColor: theme.palette.common.white,
  },
  logoWrapper: {
    display: 'flex',
    margin: 'auto',
    marginTop: theme.spacing(2),
    maxWidth: 200,
    maxHeight: 200,
    marginBottom: theme.spacing(2),
  },
  logo: {
    minWidth: '250px',
    width: '46%',
    maxWidth: '100%',
    height: 'auto',
    margin: theme.spacing(5, 'auto'),
    [theme.breakpoints.down('md')]: {
      minWidth: '200px',
    },
  },
}));

const Landing = () => {
  const classes = useStyles();
  const isAuthenticated = useIsAuthenticated();

  return (
    <Navigation maxWidth={false} topbarVariant={'transparent'}>
      <Helmet>
        <title>Home - ROOMMAKER</title>
      </Helmet>
      <div className={classes.cover}>
        <div className={classes.img} />
        <Typography align='center' color='inherit' variant='h1'>
          ROOMBOOKER
        </Typography>
        <Typography align='center' color='inherit' variant='h3'>
          Simple, straight forward, no hassle room bookings.
        </Typography>
        {!isAuthenticated && (
          <div className={classes.btnGroup}>
            <Button className={classes.button} component={Link} to={URLS.LOGIN} variant='outlined'>
              Log in
            </Button>
            <Button className={classes.button} component={Link} to={URLS.SIGNUP} variant='outlined'>
              Sign up
            </Button>
          </div>
        )}
      </div>
    </Navigation>
  );
};

export default Landing;
