// Material UI Components
import { makeStyles } from '@material-ui/core/styles';
import { Card, CardContent, Typography } from '@material-ui/core/';

// Project Components
import { SectionsList } from 'types/Types';
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

export type SectionCardProps = {
  section: SectionsList;
};

const SectionCard = ({ section }: SectionCardProps) => {
  const classes = useStyles();

  return (
    <Card className={classes.root}>
      <CardContent className={classes.cardContent}>
        <div>
          <Typography component='h2' gutterBottom variant='h4'>
            {section.name}
          </Typography>
          <Typography className={classes.description} variant='h5'>
            Capacity: {section.capacity}
          </Typography>
        </div>
      </CardContent>
    </Card>
  );
};

export default SectionCard;
