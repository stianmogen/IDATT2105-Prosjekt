import { Room, SectionId } from 'types/Types';
import { useMemo } from 'react';
// Material UI Components
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import Skeleton from '@material-ui/core/Skeleton';
import LocalizationProvider from '@material-ui/lab/LocalizationProvider/LocalizationProvider';
import AdapterDateFns from '@material-ui/lab/AdapterDateFns';
import { MenuItem } from '@material-ui/core';

// Project Components
import Paper from 'components/layout/Paper';
import MasonryGrid from 'components/layout/MasonryGrid';
import SectionCard from 'components/layout/SectionCard';
import Pagination from 'components/layout/Pagination';
import NotFoundIndicator from 'components/miscellaneous/NotFoundIndicator';
import { useSections } from 'hooks/Sections';
import DatePicker from 'components/inputs/DatePicker';
import { SubmitHandler, useForm } from 'react-hook-form';
import Select from 'components/inputs/Select';
import SubmitButton from 'components/inputs/SubmitButton';
import TextField from 'components/inputs/TextField';
import { useCreateRoomReservation } from 'hooks/Rooms';

const useStyles = makeStyles((theme) => ({
  image: {
    borderRadius: theme.shape.borderRadius,
    border: `1px solid ${theme.palette.divider}`,
  },
  rootGrid: {
    display: 'grid',
    position: 'relative',
    alignItems: 'self-start',
  },
  grid: {
    display: 'grid',
    gridTemplateColumns: '1fr 1fr',
    gridGap: theme.spacing(2),
    alignItems: 'self-start',
    [theme.breakpoints.down('md')]: {
      gridGap: theme.spacing(1),
    },
  },
  details: {
    padding: theme.spacing(1, 2),
    width: '100%',
  },
  detailsHeader: {
    fontSize: '1.5rem',
  },
  titleRow: {
    gridTemplateColumns: '1fr',
  },
  title: {
    color: theme.palette.text.primary,
    fontSize: '2.4rem',
    wordWrap: 'break-word',
  },
  description: {
    whiteSpace: 'break-spaces',
  },
  skeleton: {
    maxWidth: '100%',
    borderRadius: theme.shape.borderRadius,
  },
  img: {
    width: '100%',
    borderRadius: theme.shape.borderRadius,
  },
  topGrid: {
    display: 'flex',
  },
  paperMargin: {
    marginBottom: theme.spacing(1),
  },
}));

export type RoomRendererProps = {
  room: Room;
  preview?: boolean;
};

type FormValues = {
  startTime: Date;
  endTime: Date;
  participants: number;
  sections: SectionId;
};

const RoomRenderer = ({ room }: RoomRendererProps) => {
  const classes = useStyles();
  const { data, error, hasNextPage, fetchNextPage, isFetching } = useSections(room.id);
  const sections = useMemo(() => (data !== undefined ? data.pages.map((page) => page.content).flat(1) : []), [data]);
  const isEmpty = useMemo(() => !sections.length && !isFetching, [sections, isFetching]);
  const { control, formState, register, handleSubmit, setError } = useForm<FormValues>();
  const createBooking = useCreateRoomReservation(room.id);
  const submit: SubmitHandler<FormValues> = async (data) => {
    if (data.endTime < data.startTime) {
      setError('endTime', { message: 'Date range is not valid' });
      return;
    }
    const roomBooking = {
      ...data,
      startTime: data.startTime.toJSON(),
      endTime: data.endTime.toJSON(),
      participants: data.participants,
      sectionsIds: [data.sections],
    };
    createBooking.mutate(roomBooking);

    // eslint-disable-next-line no-console
    console.log(roomBooking);
  };

  return (
    <div className={classes.rootGrid}>
      <div className={classes.topGrid}>
        <Typography className={classes.title} gutterBottom variant='h1'>
          Room: {room.name}
        </Typography>
      </div>
      <div className={classes.grid}>
        <div>
          <Paper className={classes.paperMargin}>
            <div>
              <Typography className={classes.detailsHeader} variant='h2'>
                Location
              </Typography>
              <Typography>{`Building: ${room.building.name}`}</Typography>
              <Typography>{`Address: ${room.building.address}`}</Typography>
              <Typography>{`Floor level: ${room.level} `}</Typography>
            </div>
          </Paper>
          <Paper className={classes.paperMargin}>
            <form onSubmit={handleSubmit(submit)}>
              <Typography className={classes.detailsHeader} variant='h2'>
                Book Section
              </Typography>
              <Select control={control} formState={formState} label='Select Section' margin='normal' name='sections'>
                {room.sections.map((value) => (
                  <MenuItem key={value.id} value={value.id}>
                    {value.name}
                  </MenuItem>
                ))}
              </Select>
              <LocalizationProvider dateAdapter={AdapterDateFns}>
                <DatePicker control={control} formState={formState} fullWidth label='From' margin='normal' name='startTime' type='date-time' />
                <DatePicker control={control} formState={formState} fullWidth label='To' margin='normal' name='endTime' type='date-time' />
              </LocalizationProvider>
              <TextField formState={formState} fullWidth label='Amount of people' {...register('participants')} />
              <SubmitButton formState={formState} variant='outlined'>
                BOOK ROOM
              </SubmitButton>
            </form>
          </Paper>
        </div>
        {/* {isLoading && <ListItemLoading />} */}
        <Pagination fullWidth hasNextPage={hasNextPage} isLoading={isFetching} nextPage={() => fetchNextPage()}>
          {isEmpty && <NotFoundIndicator header={error?.message || 'Couldnt find any sections'} />}
          {sections.map((section) => (
            <SectionCard key={section.id} section={section} />
          ))}
        </Pagination>
        {/* {isFetching && <ListItemLoading />} */}
      </div>
    </div>
  );
};

export default RoomRenderer;

export const RoomRendererLoading = () => {
  const classes = useStyles();

  return (
    <div className={classes.rootGrid}>
      <div className={classes.grid}>
        <div>
          <Skeleton className={classes.skeleton} height={80} width='60%' />
          <Skeleton className={classes.skeleton} height={40} width={250} />
          <Skeleton className={classes.skeleton} height={40} width='80%' />
          <Skeleton className={classes.skeleton} height={40} width='85%' />
          <Skeleton className={classes.skeleton} height={40} width='75%' />
          <Skeleton className={classes.skeleton} height={40} width='90%' />
        </div>
        <div className={classes.grid}>
          <Paper>
            <Skeleton className={classes.skeleton} height={80} width='60%' />
            <Skeleton className={classes.skeleton} height={40} width={250} />
            <Skeleton className={classes.skeleton} height={40} width='80%' />
            <Skeleton className={classes.skeleton} height={40} width='85%' />
          </Paper>
        </div>
      </div>
      <div className={classes.grid}>
        <MasonryGrid>
          <Skeleton className={classes.img} height={150} />
          <Skeleton className={classes.img} height={200} />
          <Skeleton className={classes.img} height={180} />
          <Skeleton className={classes.img} height={120} />
        </MasonryGrid>
      </div>
    </div>
  );
};
