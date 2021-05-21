import { useEffect } from 'react';
import Helmet from 'react-helmet';
import classnames from 'classnames';
import { useUser } from 'hooks/User';
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

const AdminPage = () => {
  const classes = useStyles();
  const { userId }: { userId?: string } = useParams();
  const { data: signedInUser } = useUser();
  const { data: user, isLoading, isError } = useUser(userId);

  //const tabs = reservationTab;
  const navigate = useNavigate();

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

  return (
    <Navigation maxWidth={false}>
      <Helmet>
        <title>{`${user.firstName} ${user.surname} - My Bookings `}</title>
      </Helmet>
      <div className={classes.backgroundImg} />
      <Container className={classnames(classes.grid, classes.root)}>
        <Typography variant='h1'>Admin Page</Typography>
      </Container>
    </Navigation>
  );
};
export default AdminPage;
