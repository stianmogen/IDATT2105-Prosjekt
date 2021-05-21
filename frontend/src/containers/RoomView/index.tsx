import { useEffect } from 'react';
import Helmet from 'react-helmet';
import { useParams, useNavigate } from 'react-router-dom';
import URLS from 'URLS';
import { urlEncode } from 'utils';
import { useRoomById } from 'hooks/Rooms';
import { makeStyles } from '@material-ui/core/styles';

// Project components
import Http404 from 'containers/Http404';
import Navigation from 'components/navigation/Navigation';
import RoomRenderer, { RoomRendererLoading } from 'containers/RoomView/components/RoomRenderer';

const useStyles = makeStyles((theme) => ({
  wrapper: {
    padding: theme.spacing(2),
    [theme.breakpoints.down('md')]: {
      padding: theme.spacing(1),
    },
  },
}));

const RoomDetails = () => {
  const classes = useStyles();
  const { id } = useParams();
  const { data, isLoading, isError } = useRoomById(id);
  const navigate = useNavigate();

  useEffect(() => {
    if (data) {
      navigate(`${URLS.ROOMS}${id}/${urlEncode(data.name)}/`, { replace: true });
    }
  }, [id, data, navigate]);

  if (isError) {
    return <Http404 />;
  }

  return (
    <Navigation topbarVariant='dynamic'>
      {data && (
        <Helmet>
          <title>{data.name} - ROOMBOOKER</title>
          <meta content={data.name} property='og:title' />
          <meta content='website' property='og:type' />
          <meta content={window.location.href} property='og:url' />
        </Helmet>
      )}
      <div className={classes.wrapper}>{isLoading ? <RoomRendererLoading /> : data !== undefined && <RoomRenderer room={data} />}</div>
    </Navigation>
  );
};

export default RoomDetails;
