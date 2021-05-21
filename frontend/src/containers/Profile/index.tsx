import { useEffect, useState } from 'react';
import { urlEncode } from 'utils';
import Helmet from 'react-helmet';
import classnames from 'classnames';
import { useUser, useLogout } from 'hooks/User';
import { Link, useParams, useNavigate } from 'react-router-dom';
import URLS from 'URLS';

// Material UI Components
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import Button from '@material-ui/core/Button';
import Avatar from '@material-ui/core/Avatar';
import Collapse from '@material-ui/core/Collapse';

// Icons
import EditIcon from '@material-ui/icons/EditRounded';
import AktivitiesIcon from '@material-ui/icons/DateRangeRounded';
import FollowIcon from '@material-ui/icons/ClearAllRounded';
import PostsIcon from '@material-ui/icons/ViewAgendaRounded';

// Project Components
import Navigation from 'components/navigation/Navigation';
import Container from 'components/layout/Container';
import Paper from 'components/layout/Paper';
import Tabs from 'components/layout/Tabs';
import Http404 from 'containers/Http404';
import MyReservations from 'containers/Profile/components/MyReservations';

import BACKGROUND from 'assets/img/snow_mountains.jpg';

const useStyles = makeStyles((theme) => ({
  backgroundImg: {
    background: `${theme.palette.colors.gradient}, url(${BACKGROUND}) center center/cover no-repeat scroll`,
    width: '100%',
    height: 300,
    backgroundSize: 'cover',
  },
  avatarContainer: {
    position: 'relative',
    marginTop: -120,
    zIndex: 2,
    alignItems: 'center',
    [theme.breakpoints.down('lg')]: {
      marginTop: -80,
    },
  },
  avatar: {
    height: 120,
    width: 120,
    margin: 'auto',
    fontSize: '3rem',
    [theme.breakpoints.down('md')]: {
      height: 100,
      width: 100,
      fontSize: '2rem',
    },
  },
  grid: {
    display: 'grid',
    gap: theme.spacing(1),
    alignItems: 'self-start',
  },
  root: {
    gridTemplateColumns: '300px 1fr',
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

const Profile = () => {
  const classes = useStyles();
  const { userId }: { userId?: string } = useParams();
  const { data: signedInUser } = useUser();
  const { data: user, isLoading, isError } = useUser(userId);
  const logout = useLogout();
  const reservationTab = { value: 'reservations', label: 'Reservations' };

  //const tabs = reservationTab;
  const navigate = useNavigate();

  useEffect(() => {
    if (user && signedInUser && user.id === signedInUser.id) {
      navigate(`${URLS.BOOKINGS}`, { replace: true });
    } else if (userId && user) {
      navigate(`${URLS.BOOKINGS}${user.id}/${urlEncode(`${user.firstName} ${user.surname}`)}/`, { replace: true });
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
        <title>{`${user.firstName} ${user.surname} - `}</title>
      </Helmet>
      <div className={classes.backgroundImg} />
      <Container className={classnames(classes.grid, classes.root)}>
        <div className={classes.grid}>
          <Avatar className={classes.avatar}>{`${user.firstName.substr(0, 1)}${user.surname.substr(0, 1)}`}</Avatar>
          <div>
            <Typography align='center' variant='h2'>{`${user.firstName} ${user.surname}`}</Typography>
            <Typography align='center' variant='subtitle2'>
              {user.email}
            </Typography>
          </div>
        </div>
        <div className={classes.grid}>
          <div>
            <MyReservations userId={userId} />
          </div>
        </div>
      </Container>
    </Navigation>
  );
};
export default Profile;
