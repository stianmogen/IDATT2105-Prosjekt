// Material UI Components
import { makeStyles } from '@material-ui/core/styles';
import { Card, CardActionArea, CardContent, CardMedia, Typography } from '@material-ui/core/';

// Project Components
import { Link } from 'react-router-dom';
import URLS from 'URLS';
import { SectionsList } from 'types/Types';

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

export type SectionCardProps = {
  section: SectionsList;
};

const SectionCard = ({ section }: SectionCardProps) => {
  const classes = useStyles();

  return (
    <Card className={classes.root}>
      <CardActionArea component={Link} to={`${URLS.ROOMS}${section.id}/`}>
        <CardMedia className={classes.media} image={LOGO} />
        <CardContent className={classes.cardContent}>
          <div>
            <Typography component='h2' gutterBottom variant='h4'>
              {section.name}
            </Typography>
            <Typography className={classes.description} variant='h5'>
              {section.capacity}
            </Typography>
          </div>
        </CardContent>
      </CardActionArea>
    </Card>
  );
};

export default SectionCard;
