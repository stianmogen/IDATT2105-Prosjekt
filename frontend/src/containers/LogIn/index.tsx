import { useForm } from 'react-hook-form';
import { Link, useNavigate } from 'react-router-dom';
import Helmet from 'react-helmet';
import URLS from 'URLS';
import { useLogin } from 'hooks/User';

// Material UI Components
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import LinearProgress from '@material-ui/core/LinearProgress';

// Project Components
import Navigation from 'components/navigation/Navigation';
import Paper from 'components/layout/Paper';
import Logo from 'components/miscellaneous/Logo';
import SubmitButton from 'components/inputs/SubmitButton';
import TextField from 'components/inputs/TextField';

const useStyles = makeStyles((theme) => ({
  paper: {
    maxWidth: theme.breakpoints.values.sm,
    margin: theme.spacing(2, 'auto'),
  },
  logo: {
    height: 30,
    width: 'auto',
    marginBottom: theme.spacing(1),
  },
  progress: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
  },
  buttons: {
    display: 'grid',
    gridTemplateColumns: '1fr 1fr',
    gap: theme.spacing(1),
  },
  button: {
    marginTop: theme.spacing(2),
  },
}));

type LoginData = {
  email: string;
  password: string;
};

const LogIn = () => {
  const classes = useStyles();
  const navigate = useNavigate();
  const logIn = useLogin();
  const { register, formState, handleSubmit, setError } = useForm<LoginData>();

  const onLogin = async (data: LoginData) => {
    logIn.mutate(
      { email: data.email, password: data.password },
      {
        onSuccess: () => {
          navigate(URLS.LANDING);
        },
        onError: (e) => {
          setError('password', { message: e.detail || 'Noe gikk galt' });
        },
      },
    );
  };

  return (
    <Navigation>
      <Helmet>
        <title>Log in</title>
      </Helmet>
      <Paper className={classes.paper}>
        {logIn.isLoading && <LinearProgress className={classes.progress} />}
        <Logo darkColor={'white'} lightColor={'black'} />
        <Typography variant='h3'>Log in</Typography>
        <form onSubmit={handleSubmit(onLogin)}>
          <TextField
            disabled={logIn.isLoading}
            formState={formState}
            label='Brukernavn'
            {...register('email', {
              validate: (value: string) => (value.includes('@') ? 'Bruk brukernavn, ikke epost' : undefined),
            })}
          />
          <TextField disabled={logIn.isLoading} formState={formState} label='Passord' {...register('password')} type='password' />
          <SubmitButton className={classes.button} disabled={logIn.isLoading} formState={formState}>
            Log in
          </SubmitButton>
          <div className={classes.buttons}>
            <Button className={classes.button} color='primary' component={Link} disabled={logIn.isLoading} fullWidth to={URLS.LOGIN}>
              Forgot password?
            </Button>
            <Button className={classes.button} color='primary' component={Link} disabled={logIn.isLoading} fullWidth to={URLS.LOGIN}>
              Create user
            </Button>
          </div>
        </form>
      </Paper>
    </Navigation>
  );
};

export default LogIn;
