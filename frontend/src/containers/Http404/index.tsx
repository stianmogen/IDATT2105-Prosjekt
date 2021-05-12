import Helmet from 'react-helmet';
import { Link } from 'react-router-dom';
import URLS from 'URLS';
import { useIsAuthenticated } from 'hooks/User';

// Material UI Components
import { makeStyles } from '@material-ui/core/styles';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';

// Project Components
import Navigation from 'components/navigation/Navigation';

const useStyles = makeStyles((theme) => ({
  img: {
    width: '100%',
    maxHeight: '70vh',
    objectFit: 'contain',
  },
  imgPadding: {
    paddingTop: theme.spacing(3),
  },
  buttons: {
    margin: theme.spacing(2, 'auto'),
    display: 'grid',
    gap: theme.spacing(1),
    maxWidth: 200,
  },
}));

const Http404 = () => {
  const classes = useStyles();
  const isAuthenticated = useIsAuthenticated();

  return (
    <Navigation>
      <Helmet>
        <title>404</title>
      </Helmet>
      <img alt='404' className={classes.img} src='https://via.placeholder.com/360x360.png' />
      <Typography align='center' variant='h1'>
        {isAuthenticated ? 'Kunne ikke finne siden' : 'Du er innlogget, men kunne fremdeles ikke finne siden :('}
      </Typography>
      <div className={classes.buttons}>
        <Button color='primary' component={Link} to={URLS.LANDING} variant='contained'>
          Til forsiden
        </Button>
        <Button color='primary' component={Link} to={URLS.LOGIN} variant='outlined'>
          Log in
        </Button>
      </div>
    </Navigation>
  );
};

export default Http404;
