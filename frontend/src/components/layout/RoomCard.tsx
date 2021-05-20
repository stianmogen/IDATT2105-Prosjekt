// Material UI Components
import { makeStyles } from '@material-ui/core/styles';
import { Card, CardActionArea, CardContent, CardMedia, Typography } from '@material-ui/core/';

// Project Components
import { Link } from 'react-router-dom';
import URLS from 'URLS';
import { RoomList } from 'types/Types';

// Images
import LOGO from 'assets/img/DefaultBackground.jpg';

const useStyles = makeStyles((theme) => ({
  root: {
    marginBottom: theme.spacing(1),
  },
  media: {
    height: 90,
  },
  link: {
    textDecoration: 'none',
  },
  description: {
    overflow: 'hidden',
    '-webkit-line-clamp': 4,
    display: '-webkit-box',
    '-webkit-box-orient': 'vertical',
    whiteSpace: 'break-spaces',
  },
  cardContent: {
    display: 'grid',
    gridTemplateColumns: '1fr auto',
  },
}));

export type RoomCardProps = {
  room: RoomList;
};

const RoomCard = ({ room }: RoomCardProps) => {
  const classes = useStyles();

  return (
    <Card className={classes.root}>
      <CardActionArea component={Link} to={`${URLS.ROOMS}${room.id}/`}>
        <CardMedia className={classes.media} image={LOGO} />
        <CardContent className={classes.cardContent}>
          <div>
            <Typography component='h2' gutterBottom variant='h4'>
              {room.name}
            </Typography>
            <Typography className={classes.description} variant='h5'>
              {room.building.name}
            </Typography>
            <Typography className={classes.description} variant='h5'>
              Address: {room.building.address}
            </Typography>
          </div>
          <div>
            <Typography component='h2' gutterBottom variant='h3'>
              {room.sections[0].capacity}
            </Typography>
          </div>
        </CardContent>
      </CardActionArea>
    </Card>
  );
};

export default RoomCard;
