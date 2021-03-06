// Material UI Components
import { makeStyles } from '@material-ui/core/styles';
import { Card, CardContent, Typography } from '@material-ui/core/';

// Project Components
import { ReservationList } from 'types/Types';
import { formatDate } from 'utils';
// Images
import classnames from 'classnames';
import { parseISO } from 'date-fns';

const useStyles = makeStyles((theme) => ({
  root: {
    marginBottom: theme.spacing(1),
  },
  fullHeight: {
    height: '100%',
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

export type ReservationCardPrompts = {
  reservation: ReservationList;
  fullHeight?: boolean;
};

const ReservationCard = ({ reservation, fullHeight }: ReservationCardPrompts) => {
  const classes = useStyles();
  const startDate = parseISO(reservation.startTime);
  const endDate = parseISO(reservation.endTime);

  return (
    <Card className={classnames(classes.root, fullHeight && classes.fullHeight)}>
      <CardContent className={classes.cardContent}>
        <div>
          <Typography component='h2' gutterBottom variant='h4'>
            {reservation.sections[0].name}
          </Typography>
          <Typography className={classes.description} variant='body2'>
            {`Fra: ${formatDate(startDate)}`}
          </Typography>
          <Typography className={classes.description} variant='body2'>
            {`Til: ${formatDate(endDate)}`}
          </Typography>
          <Typography className={classes.description} variant='body2'>
            {reservation.participants + ' participants'}
          </Typography>
          <Typography className={classes.description} variant='body2'>
            {reservation.description}
          </Typography>
        </div>
      </CardContent>
    </Card>
  );
};

export default ReservationCard;
