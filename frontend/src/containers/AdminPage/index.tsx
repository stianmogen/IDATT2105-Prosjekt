import { useEffect } from 'react';
import Helmet from 'react-helmet';
import classnames from 'classnames';
import { useChangeRole, useUser } from 'hooks/User';
import { useParams, useNavigate } from 'react-router-dom';
import URLS from 'URLS';

// Material UI Components
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';

// Project Components
import Navigation from 'components/navigation/Navigation';
import Container from 'components/layout/Container';
import Http404 from 'containers/Http404';

import BACKGROUND from 'assets/img/DefaultBackground.jpg';
import Paper from 'components/layout/Paper';
import TextField from 'components/inputs/TextField';
import { useForm } from 'react-hook-form';
import Select from 'components/inputs/Select';
import { MenuItem } from '@material-ui/core';
import SubmitButton from 'components/inputs/SubmitButton';
import useSnackbar from 'hooks/Snackbar';

const useStyles = makeStyles((theme) => ({
  backgroundImg: {
    background: `${theme.palette.colors.gradient}, url(${BACKGROUND}) center center/cover no-repeat scroll`,
    width: '100%',
    height: 300,
    backgroundSize: 'cover',
    marginBottom: theme.spacing(2),
  },
  grid: {
    display: 'grid',
    gap: theme.spacing(1),
    alignItems: 'self-start',
  },
  root: {
    gridTemplateColumns: '1fr',
    gap: theme.spacing(2),
    [theme.breakpoints.down('lg')]: {
      gridTemplateColumns: '1fr',
    },
  },
  logout: {
    color: theme.palette.error.main,
    borderColor: theme.palette.error.main,
    '&:hover': {
      color: theme.palette.error.light,
      borderColor: theme.palette.error.light,
    },
  },
}));

type UserRoleData = {
  email: string;
  role: string;
};

const AdminPage = () => {
  const classes = useStyles();
  const { userId }: { userId?: string } = useParams();
  const { data: signedInUser } = useUser();
  const { data: user, isLoading, isError } = useUser(userId);
  const changeRole = useChangeRole();
  const { formState, handleSubmit, register, control } = useForm<UserRoleData>();
  //const tabs = reservationTab;
  const navigate = useNavigate();
  const showSnackbar = useSnackbar();

  useEffect(() => {
    if (user && signedInUser && user.id === signedInUser.id) {
      navigate(`${URLS.ADMIN}`, { replace: true });
    } else if (userId && user) {
      navigate(`${URLS.ADMIN}`, { replace: true });
    }
  }, [user, signedInUser, navigate]);

  if (isError) {
    return <Http404 />;
  }
  if (isLoading || !user) {
    return <Navigation isLoading />;
  }
  const submit = async (data: UserRoleData) => {
    changeRole.mutate(
      { email: data.email, role: data.role },
      {
        onSuccess: () => {
          showSnackbar('The role was changed!', 'success');
        },
        onError: (e) => {
          showSnackbar(e.message, 'error');
        },
      },
    );
  };

  return (
    <Navigation maxWidth={false}>
      <Helmet>
        <title>{'Admin - ROOMBOOKER'}</title>
      </Helmet>
      <div className={classes.backgroundImg} />
      <Container className={classnames(classes.grid, classes.root)}>
        <Typography variant='h1'>Admin Page</Typography>
        <Paper>
          <Typography variant='h2'>Change roles</Typography>
          <form onSubmit={handleSubmit(submit)}>
            <TextField formState={formState} label='Email' {...register('email', { required: 'Field is required' })} required />
            <Select control={control} formState={formState} label='Role' margin='normal' name='role'>
              <MenuItem value='ROLE_USER'>User</MenuItem>
              <MenuItem value='ROLE_MODERATOR'>Moderator</MenuItem>
              <MenuItem value='ROLE_ADMIN'>Admin</MenuItem>
            </Select>
            <SubmitButton formState={formState}>CHANGE ROLE</SubmitButton>
          </form>
        </Paper>
      </Container>
    </Navigation>
  );
};
export default AdminPage;
